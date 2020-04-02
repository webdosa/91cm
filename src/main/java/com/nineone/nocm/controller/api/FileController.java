package com.nineone.nocm.controller.api;

import com.nineone.nocm.annotation.Socialuser;
import com.nineone.nocm.domain.ApiResponse;
import com.nineone.nocm.domain.ContentsFile;
import com.nineone.nocm.domain.Message;
import com.nineone.nocm.domain.User;
import com.nineone.nocm.service.FileStorageService;
import com.nineone.nocm.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private MessageService messageService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FileStorageService fileStorageService;

    // Exception 처리 필요
    @PostMapping("/upload")
    @Transactional
    public ResponseEntity<?> uploadFile(@RequestParam("files") MultipartFile[] files,
                                        @RequestParam("channel_id") int channel_id,
                                        @RequestParam("sender") String sender, @Socialuser User user) {

        Message message = Message.builder().channel_id(channel_id)
                .sender(sender)
                .user(user)
                .build();
        message.setSend_date(messageService.makeDate());
        message.setStr_send_date(messageService.makeStrDate(message.getSend_date()));
        messageService.insertMessage(message);
        List<ContentsFile> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            ContentsFile contentsFile = ContentsFile.getDefaultInstance(file);
            log.info(contentsFile.getOriginal_name());
            contentsFile.setSender(sender);
            contentsFile.setMessage_id(message.getId());
            fileList.add(contentsFile);
            fileStorageService.storeFile(file, contentsFile);
            fileStorageService.DBStoreFile(contentsFile);
        }
        message.setFiles(fileList);
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getChannel_id(), message);
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/download/")
//                .path(fileName)
//                .toUriString();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "fileName") String fileName) {
        // id를 통해서 UUID로 인코딩된 file을 가져오는 로직
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("알 수 없는 파일");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment").filename(resource.getFilename(), StandardCharsets.UTF_8).build();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(resource);

    }
}

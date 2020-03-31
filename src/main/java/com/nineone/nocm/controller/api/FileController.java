package com.nineone.nocm.controller.api;

import com.nineone.nocm.annotation.Socialuser;
import com.nineone.nocm.domain.ContentsFile;
import com.nineone.nocm.domain.Message;
import com.nineone.nocm.domain.User;
import com.nineone.nocm.service.FileStorageService;
import com.nineone.nocm.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("channel_id") int channel_id,
                                        @RequestParam("sender") String sender, @Socialuser User user) {
        String UUID = fileStorageService.getUUID();
        ContentsFile contentsFile = ContentsFile.builder()
                .original_name(file.getOriginalFilename())
                .extension(file.getOriginalFilename().split("\\.")[1])
                .server_name(UUID)
                .sender(sender)
                .path("/api/file/download/" + UUID)
                .build();
        Message message = Message.builder().channel_id(channel_id)
                .sender(sender)
                .user(user)
                .build();
        message.setSend_date(messageService.makeDate());
        message.setStr_send_date(messageService.makeStrDate(message.getSend_date()));
        List<ContentsFile> files = new ArrayList<>();
        files.add(contentsFile);
        message.setFiles(files);
        messageService.insertMessage(message);
        contentsFile.setMessage_id(message.getId());
        fileStorageService.storeFile(file, contentsFile);
        fileStorageService.DBStoreFile(contentsFile);


        messagingTemplate.convertAndSend("/sub/chat/room/"+message.getChannel_id(), message);
//        String fileName = fileStorageService.StoreFile(file);
//        log.info(fileName);
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
        log.info(contentDisposition.toString());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(resource);

    }
}

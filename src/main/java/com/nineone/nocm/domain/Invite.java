package com.nineone.nocm.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Invite {
   private int id;
   private int channel_id;
   private String sender;
   private String recipient;
   private String send_date;

   @Builder
   public Invite(int id, int channel_id, String sender,
                 String recipient, String send_date){
      this.id = id;
      this.channel_id = channel_id;
      this.sender = sender;
      this.recipient = recipient;
      this.send_date = send_date;
   }
}

package com.nineone.nocm.domain;

import com.nineone.nocm.domain.enums.InviteState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Invite {
   private int id;
   private int channel_id;
   private String sender;
   private String recipient;
   private String send_date;
   private InviteState invite_state;
   @Builder
   public Invite(int id, int channel_id, String sender,
                 String recipient, String send_date, InviteState invite_state){
      this.id = id;
      this.channel_id = channel_id;
      this.sender = sender;
      this.recipient = recipient;
      this.send_date = send_date;
      this.invite_state = invite_state;
   }
}

import { Component } from '@angular/core';
import {roomMappings} from "../../../../global/constants";

@Component({
  selector: 'app-room-mapping',
  templateUrl: './room-mapping.component.html',
  styleUrls: ['./room-mapping.component.scss']
})
export class RoomMappingComponent {
  roommapping = roomMappings.filter(roomMapping => !(roomMapping.address == "") && !(roomMapping.roomName == ""));
}

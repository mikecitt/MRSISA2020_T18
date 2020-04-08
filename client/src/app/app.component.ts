import { Component } from '@angular/core';

import { EchoService } from './echo.service'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  time = null
  message = null

  constructor(private echoService : EchoService) {}

  postEcho() {
  	this.echoService.getEcho().subscribe(data => {
  		this.time = data['time']
  		this.message = data['message']
  	})
  }
}

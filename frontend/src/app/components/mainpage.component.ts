import { Component, OnInit, ViewChild } from '@angular/core';
import { Calendar, CalendarOptions, DateSelectArg, EventApi, EventClickArg, EventInput, EventSourceFuncArg } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import { Router } from '@angular/router';
import { StocksRepository } from '../stocks.repository';

@Component({
  selector: 'app-mainpage',
  templateUrl: './mainpage.component.html',
  styleUrls: ['./mainpage.component.css']
})
export class MainpageComponent implements OnInit {



  @ViewChild('calendar')
  calender!: Calendar

  constructor(private router: Router, private stocksRepo: StocksRepository) {}

  ngOnInit(): void {
    
  }


  calendarOptions: CalendarOptions = {
    plugins: [      
      dayGridPlugin,
      interactionPlugin
      //CAN CONSIDER bootstrapPlugin
    ],

    initialView: 'dayGridMonth',
    events: this.loadEvents.bind(this), 
    weekends: true,
    editable: true,
    selectable: true,
    selectMirror: true,
    dayMaxEvents: true,
    eventColor: 'green',
    eventDisplay: 'block',
    eventTextColor: 'black',
    contentHeight: 400, 
    dateClick: this.dateClicked.bind(this),
    eventClick: this.handleEventClick.bind(this),

  };




  


  // events = [
  //   { id:'1', date: '2023-02-01',  color: "#3F51B5",  calories: 1659},
  //   { id:'2', date: '2023-02-03',  color: "#FF4081",  calories: 1659},
  //   { id:'3', date: '2023-02-04',  color: "#3F51B5",  calories: 1659},
  //   { id:'4', date: '2023-02-05',  color: "#3F51B5",  calories: 1659},
  //   { id:'5', date: '2023-02-06',  color: "#3F51B5",  calories: 1659},
  //   { id:'6', date: '2023-02-07',  color: "#3F51B5",  calories: 1659},
  //   { id:'7', date: '2023-02-08',  color: "#3F51B5",  calories: 1659},
  //   { id:'8', date: '2023-02-09',  color: "#FF4081",  calories: 1659},
  //   { id:'9', date: '2023-02-10',  color: "#3F51B5",  calories: 1659},
  //   { id:'10', date: '2023-02-02',  color: "#3F51B5",  calories: 2655 },
  //   { date: '2023-03-02',  color: "#3F51B5",  calories: 2655 },
  // ]
  // events: Day[] = []


}

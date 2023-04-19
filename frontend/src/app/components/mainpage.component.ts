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
    //Set the event property
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
    dateClick: this.clickDate.bind(this),
    eventClick: this.clickEvent.bind(this),

  };


  //Load the day_id of each of the day that is taken from List<Day>
  //Refer to EventSourceFuncArg below
  loadEvents(args: EventSourceFuncArg) : Promise<EventInput[]> {
    return new Promise<EventInput[]> ((resolve) => {
      //args.startStr and args.endStr: Defined by the FullCalendar mod. 
      //Will use these to get the Days within the start and end date
      this.stocksRepo.getDays(args.startStr, args.endStr).then(days => {
        var events: EventInput[] = [];
        if (days[0] != null) days.forEach( day => {
          var colorCode = "#808080" //Grey
          if (Number(day.pnl) > 0) colorCode = "#3fb541" //Green
          if (Number(day.pnl) < 0) colorCode = "#DD1F13" //Red

          events.push({
            day_id: day.day_id,
            pnl: day.pnl,
            date: day.date,
            color: colorCode,
          })
          //To fufill the Promise
          resolve(events)
        })
      }).catch(error => {
          console.error(error)
        })
    })
  }

  //Click on the date
  //To fill up a journal entry
  clickDate(clickInfo: any){
    console.log(">>> ClickDate. Info:", clickInfo)
    this.router.navigate(['/entry', clickInfo.dateStr])

  }

  //When clicked on the pnl
  clickEvent(clickInfo: EventClickArg) {
    
    //clickInfo.event.startStr returns: YYYY-MM-DDTHH:mm:ss+HH:mm
    console.log(">>> ClickEvent - Date:", clickInfo.event.startStr)
    //Returns the additional properties associated with the event
    console.info(">>> ClickEvent - Additional Props:", clickInfo.event.extendedProps)
    this.router.navigate(['/uploadedentries', clickInfo.event.startStr, clickInfo.event.extendedProps['day_id']])
  }

  logout() {
    this.router.navigate(['/'])
  }

  getQuotes() {
    this.router.navigate(['/quotes'])
  }

  mainpage() {
    this.router.navigate(['/mainpage'])
  }

  contactus() {
    this.router.navigate(['/contactus'])
  }

  


}

/*
EventSourceFuncArg:
start: a Date object representing the start date of the requested event range.
end: a Date object representing the end date of the requested event range.
startStr: a string representing the start date of the requested event range in ISO format (YYYY-MM-DDTHH:mm:ss).
endStr: a string representing the end date of the requested event range in ISO format (YYYY-MM-DDTHH:mm:ss).


For calendarOptions - this.METHOD.bind(this)
Since loadEvents relies on other methods and properties defined in the same class, 
it needs to be bound to the class instance using the bind method. 
This ensures that the this keyword inside the function refers to the correct object (i.e., the instance of the class).


dateClick: is triggered when the user clicks on a date on the calendar grid that does not have an event. 
It provides a way to respond to clicks on empty dates in the calendar, for example, by adding a new event or 
performing some other action.

eventClick: on the other hand, is triggered when the user clicks on an event on the calendar. 
It provides a way to respond to clicks on existing events, for example, by displaying more information 
about the event or allowing the user to edit or delete the event.

*/
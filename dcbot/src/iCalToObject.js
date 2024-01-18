const ical = require("ical");

class ICalToObject {
  static getTodaysEvents(icalString) {
    var eventStrings = [];

    const data = ical.parseICS(icalString);
    for (let k in data) {
      if (data.hasOwnProperty(k)) {
        var ev = data[k];
        if (data[k].type == "VEVENT") {
          const date = new Date();
          if (
            ev.start.getDate() === date.getDate() &&
            ev.start.getFullYear() === date.getFullYear() &&
            ev.start.getMonth() === date.getMonth()
          ) {
            var s = "";
            s = s + `${ev.summary} `;
            s = s + `${ev.description} `;
            s = s + `${ev.start.getHours()}:`;
            if (ev.start.getMinutes() === 0) {
              s = s + `00`;
            } else {
              s = s + `${ev.start.getMinutes()}`;
            }
            s = s + "-";
            s = s + `${ev.end.getHours()}:`;
            if (ev.end.getMinutes() === 0) {
              s = s + `00`;
            } else {
              s = s + `${ev.end.getMinutes()}`;
            }
            eventStrings.push(s);
          }
        }
      }
    }
    return eventStrings;
  }
}

module.exports = ICalToObject;

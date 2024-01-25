require("dotenv").config();
const { Client, IntentsBitField } = require("discord.js");
const IcalService = require("./ical-service");
const ICalToObject = require("./iCalToObject");
const { all } = require("axios");

const client = new Client({
  intents: [
    IntentsBitField.Flags.Guilds,
    IntentsBitField.Flags.GuildMembers,
    IntentsBitField.Flags.GuildMessages,
    IntentsBitField.Flags.MessageContent,
  ],
});

var storage = [];
var guild;

client.on("ready", (c) => {
  guild = client.guilds.fetch(process.env.GUILD_ID);
  console.log(`👍${c.user.tag} is online`);
});

client.on("messageCreate", (msg) => {
  if (msg.author.bot) {
    return;
  }
});

client.on("interactionCreate", (interaction) => {
  if (!interaction.isChatInputCommand()) return;

  switch (interaction.commandName) {
    case "add-calendar":
      var name = interaction.options.get("calendar-name").value;
      var link = interaction.options.get("calendar-link").value;
      let success = false;

      const asyncOperation = new Promise(async (resolve) => {
        try {
          var iCalAsString = await IcalService.getIcal(link);
          success = true;
        } catch (error) {
          interaction.reply("Invalid calendar link ❌");
        }

        resolve();
      });

      asyncOperation.then(() => {
        if (!success) {
          return;
        }
        if (
          storage.filter((item) => {
            return item.userid === interaction.user.id && item.link === link;
          }).length == 0
        ) {
          storage.push({
            name: name,
            userid: interaction.user.id,
            link: link,
            reminders: false,
          });
          interaction.reply({
            content: `Calendar ${name} added sucessfully 👍`,
            ephemeral: true,
          });
        } else {
          interaction.reply({
            content: "Calendar is already added! 👀 ",
            ephemeral: true,
          });
        }
      });
      break;
    case "timetable":
      var finalMessage = "⌚Deine Termine für heute:🎓\n";
      var items = storage.filter((e) => {
        return e.userid === interaction.user.id;
      });

      items.forEach((item) => {
        finalMessage = finalMessage + `\n${item.name}:\n`;
        (async () => {
          try {
            var iCalAsString = await IcalService.getIcal(item.link);
            var result = ICalToObject.getTodaysEvents(iCalAsString);
            var messageBody = result.join("\n");
            finalMessage = finalMessage + messageBody;
            interaction.user.send(finalMessage);
            interaction.reply({
              content: "Timetable sent ⌚",
              ephemeral: true,
            });
          } catch (error) {
            storage = storage.filter((x) => {
              return x != item;
            });
            interaction.user.send(
              `Link for calendar ${item.name} turned invalid. \n Calendar was removed`
            );
            interaction.reply({
              content: "Error sending Timetable ❌",
              ephemeral: true,
            });
          }
        })();
      });

      break;
    case "remove-calendar":
      var name = interaction.options.get("calendar-name").value;
      if (
        storage.filter((item) => {
          return (
            item.userid === interaction.user.id &&
            item.name === interaction.options.get("calendar-name").value
          );
        }).length != 0
      ) {
        storage = storage.filter((item) => {
          return !(item.userid === interaction.user.id && item.name === name);
        });
        interaction.reply({
          content: `Calendar ${name} removed`,
          ephemeral: true,
        });
      } else {
        interaction.reply({
          content: `There is no calender ${name}`,
          ephemeral: true,
        });
      }
      break;
    case "all-calendars":
      var allcalendars = "All Calendars you have added📅:\n";
      var x = storage.filter((item) => {
        return item.userid === interaction.user.id;
      });
      if (x.length == 0) {
        interaction.user.send("You have not added any calendars yet 👀");
      } else {
        x.forEach((item) => {
          allcalendars = allcalendars + item.name + "\n";
        });
        interaction.user.send(allcalendars);
      }
      interaction.reply({ content: "👍", ephemeral: true });
      break;
    case "daily-timetable":
      let v = interaction.options.get("send-reminders").value;
      storage = storage.map((entry) => {
        if (entry.userid === interaction.user.id) {
          entry.reminders = v;
        }
        return entry;
      });
      interaction.reply({
        content: `Daily reminders set to ${v}`,
        ephemeral: true,
      });
      break;

    default:
      break;
  }
  return;
});

function sendDailyTimetables() {
  var users = storage.filter((entry) => {
    return entry.reminders;
  });

  users.forEach(async (user) => {
    let userObject  =  await client.users.fetch(user.userid);
    var finalMessage = "⌚Deine Termine für heute:🎓\n";
    finalMessage = finalMessage + `\n${user.name}:\n`;
    (async () => {
      try {
        var iCalAsString = await IcalService.getIcal(user.link);
        var result = ICalToObject.getTodaysEvents(iCalAsString);
        var messageBody = result.join("\n");
        finalMessage = finalMessage + messageBody;
        userObject.send(finalMessage);
      } catch (error) {
        storage = storage.filter((x) => {
          return x != user;
        });
        userObject.send(
          `Link for calendar ${user.name} turned invalid. \n Calendar was removed❌`
        );
      }
    })();
  });

  scheduleDailyTimetables();
}

function scheduleDailyTimetables() {
  const now = new Date();
  const targetTime = new Date(now);
  targetTime.setHours(8, 0, 0, 0);
  targetTime.setMinutes(0);
  targetTime.setSeconds(0);

  let timeUntilNext8AM = targetTime - now;
  if (timeUntilNext8AM < 0) {
    targetTime.setDate(targetTime.getDate() + 1);
    timeUntilNext8AM = targetTime - now;
  }
  setTimeout(sendDailyTimetables, timeUntilNext8AM);
}

scheduleDailyTimetables();

client.login(process.env.TOKEN);
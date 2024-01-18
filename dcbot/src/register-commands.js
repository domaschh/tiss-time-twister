require("dotenv").config();
const { REST, Routes, ApplicationCommandOptionType } = require("discord.js");

const commands = [
  {
    name: "add-calendar",
    description: "Add a calendar",
    options: [
      {
        name: "calendar-name",
        description: "Name of your calendar",
        type: ApplicationCommandOptionType.String,
        required: true,
      },
      {
        name: "calendar-link",
        description: "Link from TTT",
        type: ApplicationCommandOptionType.String,
        required: true,
      },
    ],
  },
  {
    name: "timetable",
    description: "Sends a summary of todays events",
  },
  {
    name: "remove-calendar",
    description: "Remove a calendar",
    options: [
      {
        name: "calendar-name",
        description: "Name of the calendar to delete",
        type: ApplicationCommandOptionType.String,
        required: true,
      },
    ],
  },
  {
    name: "daily-timetable",
    description: "Sends automated reminders every day",
    options: [
      {
        name: "send-reminders",
        description: "Should daily reminders be sent",
        type: ApplicationCommandOptionType.Boolean,
        required: true,
      },
    ],
  },
  {
    name: "all-calendars",
    description: "List of all added calendars",
  },
];

const rest = new REST({ version: 10 }).setToken(process.env.TOKEN);

(async () => {
  try {
    console.log("Registering commands");
    await rest.put(
      Routes.applicationGuildCommands(
        process.env.CLIENT_ID,
        process.env.GUILD_ID
      ),
      { body: commands }
    );

    console.log("Commands Registered succesfully");
  } catch (error) {
    console.log(`There was an error ${error}`);
  }
})();

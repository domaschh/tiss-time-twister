import {EventColor} from "calendar-utils";
import {
  RoomMappingComponent
} from "../components/public-config-card/public-card-fake/room-mapping/room-mapping.component";

export const colors: Record<number, EventColor> = {
  1: {
    primary: '#ff1f1f',
    secondary: '#FAE3E3',
  },
  3: {
    primary: '#03ad2b',
    secondary: '#32e65c',
  },
  0: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
  2: {
    primary: '#08c9e3',
    secondary: '#00d3ff',
  },
};

export interface RoomMapping {
  roomName: string,
  address: string
}

export const roomMappings: RoomMapping[] = [
  {
    roomName: "Boecklsaal",
    address: "Karlsplatz 13, Stiege 1, 1. OG"
  },
  {
    roomName: "Seminarraum 212-232",
    address: "Karlsplatz 13, Stiege 2"
  },
  {
    roomName: "Zeichensaal 15",
    address: "Karlsplatz 13, Hauptgeb\u00e4ude, Stiege1, 3. Stock"
  },
  {
    roomName: "Kuppelsaal",
    address: "Karlsplatz 13, 1040 Wien, 4.OG"
  },
  {
    roomName: "Prechtlsaal",
    address: "Karlsplatz 13"
  },
  {
    roomName: "Studienabteilung",
    address: "Karlsplatz 13, Erdgeschoss"
  },
  {
    roomName: "H\u00f6rsaal 14",
    address: "Karlsplatz 13, Stiege 3, 3. Stock"
  },
  {
    roomName: "HS 14A G\u00fcnther Feuerstein",
    address: "Karlsplatz 13, Stiege 3, 3. Stock"
  },
  {
    roomName: "H\u00f6rsaal 15",
    address: "Karlsplatz 13, Stiege 3, 3. Stock"
  },
  {
    roomName: "Zeichensaal 13",
    address: "Karlsplatz 13, Hauptgeb\u00e4ude, Stiege 4, 1. Stock"
  },
  {
    roomName: "Zeichensaal 14",
    address: "Karlsplatz 13, Hauptgeb\u00e4ude, Stiege 4, 1. Stock"
  },
  {
    roomName: "HS 11 Paul Ludwik",
    address: "Karlsplatz 13, Hof 1, Stiege 5"
  },
  {
    roomName: "Seminarraum 251",
    address: "Karlsplatz 13, Stiege 3"
  },
  {
    roomName: "Zeichensaal 2",
    address: "Karlsplatz 13, Hauptgeb\u00e4ude, Stiege 4, 3. Stock"
  },
  {
    roomName: "Zeichensaal 8",
    address: "Karlsplatz 13, Hauptgeb\u00e4ude, Stiege 4, 3.Stock"
  },
  {
    roomName: "Seminarraum 225",
    address: "Karlsplatz 13, Stiege 5"
  },
  {
    roomName: "HS 8 Heinz Parkus",
    address: "Karlsplatz 13, Stiege 7"
  },
  {
    roomName: "Seminarraum 202",
    address: "Karlsplatz 13, Stiege 2 (od.8)"
  },
  {
    roomName: "HS 18 Czuber",
    address: "Karlsplatz 13, zw.Stiege 2 u. 8, 2. Stock"
  },
  {
    roomName: "HS 13 Ernst Melan",
    address: "Karlsplatz 13, Stiege 7"
  },
  {
    roomName: "Seminarraum 213",
    address: "Karlsplatz 13, Stiege 2 (od.8)"
  },
  {
    roomName: "HS 17 Friedrich Hartmann",
    address: "Karlsplatz 13, Stiege 7, 3. Stock"
  },
  {
    roomName: "Seminarraum 234",
    address: "Karlsplatz 13, Stiege 2"
  },
  {
    roomName: "H\u00f6rsaal 6",
    address: "Karlsplatz 13, zw. Stiege 2 u. 8"
  },
  {
    roomName: "H\u00f6rsaal 12",
    address: "Karlsplatz 13, Hof 2, Stiege 6"
  },
  {
    roomName: "Seminarraum 7/253",
    address: "Karlsplatz 13, Stiege 7"
  },
  {
    roomName: "Seminarraum 6/253",
    address: "Karlsplatz 13"
  },
  {
    roomName: "Seminarraum 5/260",
    address: "Karlsplatz 13, Stiege 7"
  },
  {
    roomName: "Seminarraum 4/253",
    address: "Karlsplatz 13, Stiege 7, Dachgescho\u00df"
  },
  {
    roomName: "Seminarraum 3/253",
    address: "Karlsplatz 13, Stiege 7"
  },
  {
    roomName: "Seminarraum 2/253",
    address: "Karlsplatz 13, Stiege 7"
  },
  {
    roomName: "Seminarraum 1",
    address: "Karlsplatz 13, Hauptgeb\u00e4ude, Stiege 6, 4. Stock"
  },
  {
    roomName: "Zeichensaal EG Panigltrakt",
    address: "Karlsplatz 13"
  },
  {
    roomName: "Zeichensaal KG Panigltrakt",
    address: "Karlsplatz 13"
  },
  {
    roomName: "Zeichensaal 7 Ella Briggs",
    address: ""
  },
  {
    roomName: "Fachschaft Architektur",
    address: "TU Wien, Karlsplatz 13, Hof 2, EG"
  },
  {
    roomName: "Aufbaulabor",
    address: "Karlsplatz 13, Hauptgeb\u00e4ude, Stiege 7, 1.Stock"
  },
  {
    roomName: "Seminarraum 8",
    address: "Karlsplatz 13, Hof 2, Stiege 7, ebenerdig"
  },
  {
    roomName: "HS 7 Sch\u00fctte-Lihotzky",
    address: "Karlsplatz 13, Hof 2, Stiege 7"
  },
  {
    roomName: "Seminarraum 107/1",
    address: "Wiedner Hauptstr. 7"
  },
  {
    roomName: "GM 3 Vortmann H\u00f6rsaal",
    address: "Getreidemarkt 9, Bauteil BA Hochhaus, 2.OG"
  },
  {
    roomName: "Seminarraum BA 02B",
    address: "Getreidemarkt 9, Bauteil BA Hochhaus, 2.OG"
  },
  {
    roomName: "Seminarraum BA 02A",
    address: "Getreidemarkt 9, Bauteil BA Hochhaus, 2.OG"
  },
  {
    roomName: "Seminarraum BA 02C",
    address: "Getreidemarkt 9, Bauteil BA Hochhaus, 2.OG"
  },
  {
    roomName: "Seminarraum BA 03/ Sitzungszimmer Dekanat",
    address: "Getreidemarkt 9, Bauteil BA Hochhaus, 3.OG"
  },
  {
    roomName: "Seminarraum BA 05",
    address: "Getreidemarkt 9, Bauteil BA Hochhaus, 5.OG"
  },
  {
    roomName: "Seminarraum BA 08A",
    address: "Getreidemarkt 9, Bauteil BA Hochhaus, 8.OG"
  },
  {
    roomName: "Seminarraum BA 08B",
    address: "Getreidemarkt 9, Bauteil BA Hochhaus, 8.OG"
  },
  {
    roomName: "Seminarraum BA 10B",
    address: "Getreidemarkt 9, Bauteil BA Hochhaus, 10.OG"
  },
  {
    roomName: "TUtheSky",
    address: "Getreidemarkt 9, 1060 Wien, BA Geb\u00e4ude, 11.OG"
  },
  {
    roomName: "GM 1 Audi. Max.",
    address: "Getreidemarkt 9, Bauteil BA Hochhaus, EG"
  },
  {
    roomName: "GM 5 Praktikum HS",
    address: "Getreidemarkt 9, Bauteil BA Hochhaus, 2.UG"
  },
  {
    roomName: "Seminarraum Lehar 01",
    address: "Getreidemarkt 9, Bauteil BC Lehartrakt, 1.OG"
  },
  {
    roomName: "Seminarraum Lehar 02",
    address: "Getreidemarkt 9, Bauteil BC Lehartrakt, 2.OG"
  },
  {
    roomName: "Seminarraum Lehar 03",
    address: "Getreidemarkt 9, Bauteil BC Lehartrakt, 3.OG"
  },
  {
    roomName: "Seminarraum Lehar EG",
    address: "Getreidemarkt 9, Bauteil BC Lehartrakt, EG"
  },
  {
    roomName: "Leharkeller (Lernraum)",
    address: ""
  },
  {
    roomName: "GM 2 Radinger H\u00f6rsaal",
    address: "Getreidemarkt 9, Bauteil BD Hoftrakt, 1.OG"
  },
  {
    roomName: "Kleiner Schiffbau",
    address: "Getreidemarkt 9, Bauteil BD Hoftrakt, 1.OG"
  },
  {
    roomName: "Seminarraum BD 02",
    address: "Getreidemarkt 9, Bauteil BD Hoftrakt, 2.OG"
  },
  {
    roomName: "GM 4 Knoller H\u00f6rsaal",
    address: "Getreidemarkt 9, Bauteil BD Hoftrakt, 2.OG"
  },
  {
    roomName: "Seminarraum BD 02C",
    address: "Getreidemarkt 9, Bauteil BD Hoftrakt,2.OG"
  },
  {
    roomName: "Seminarraum BD 03",
    address: "Getreidemarkt 9, Bauteil BD Hoftrakt, 3.OG"
  },
  {
    roomName: "Seminarraum BE 01",
    address: "Getreidemarkt 9, Bauteil BE L\u00fcckenbau, 1.OG"
  },
  {
    roomName: "Seminarraum BH 03 Biotechnologie",
    address: "Gumpendorfer Str. 1a, Bauteil BH Gumpendorferstr., EG"
  },
  {
    roomName: "Seminarraum BH Biosciences",
    address: "Gumpendorfer Str. 1a, Bauteil BH Gumpendorferstr., EG"
  },
  {
    roomName: "Sitzungszimmer BI 04A03",
    address: "Getreidemarkt 9, Bauteil BI Loschmidttrakt,4.OG"
  },
  {
    roomName: "Seminarraum 351",
    address: "Gu\u00dfhausstr. 25-29, Stiege 1"
  },
  {
    roomName: "Seminarraum 121",
    address: "Gu\u00dfhausstr. 25-29, Stiege 1"
  },
  {
    roomName: "EI 9 Hlawka HS",
    address: "Gu\u00dfhausstr. 25-29, Stiege 1, Erdgeschoss"
  },
  {
    roomName: "EI 10 Fritz Paschke HS",
    address: "Gu\u00dfhausstr. 25-29, Stiege 1, Erdgeschoss"
  },
  {
    roomName: "Seminarraum 124",
    address: "Gu\u00dfhausstr. 25-29, Stiege 2"
  },
  {
    roomName: "Seminarraum 387",
    address: "Gu\u00dfhausstr. 25-29, Stiege 2, Erdgescho\u00df"
  },
  {
    roomName: "Seminarraum 122",
    address: "Gu\u00dfhausstr. 25-29, Stiege 3"
  },
  {
    roomName: "Seminarraum 125",
    address: "Gu\u00dfhausstr. 25-29, Stiege 1"
  },
  {
    roomName: "EI 11 Geod\u00e4sie HS",
    address: "Gu\u00dfhausstr. 25-29, Stiege 1, 3. Stock"
  },
  {
    roomName: "Seminarraum 127",
    address: "Gu\u00dfhausstr. 25-29, Stiege 1"
  },
  {
    roomName: "Seminarraum 384",
    address: "Gu\u00dfhausstr. 25-29"
  },
  {
    roomName: "EI 8 P\u00f6tzl HS",
    address: "Gu\u00dfhausstr. 25-29, Stiege 1, Erdgeschoss"
  },
  {
    roomName: "EI 7 H\u00f6rsaal",
    address: "Gu\u00dfhausstr. 25-29, Stiege 1, Erdgeschoss"
  },
  {
    roomName: "Seminarraum 354",
    address: "Gu\u00dfhausstr. 25-29, Stiege 8"
  },
  {
    roomName: "EI 3 Sahulka HS",
    address: "Gu\u00dfhausstr. 25-29, 2. Stock"
  },
  {
    roomName: "EI 3A H\u00f6rsaal",
    address: "Gu\u00dfhausstr. 25-29, 2. Stock"
  },
  {
    roomName: "EI 5 Hochenegg HS",
    address: "Gu\u00dfhausstr. 25-29, 2. Stock"
  },
  {
    roomName: "EI 2 Pichelmayer HS",
    address: "Gu\u00dfhausstr. 25-29, 2. Stock"
  },
  {
    roomName: "EI 1 Petritsch HS",
    address: "Gu\u00dfhausstr. 25-29, 2. Stock"
  },
  {
    roomName: "EI 4 Reithoffer HS",
    address: "Gu\u00dfhausstr. 25-29, 2. Stock"
  },
  {
    roomName: "EI 6 Eckert HS",
    address: "Gu\u00dfhausstr. 25-29, Stiege 10, 4. Stock"
  },
  {
    roomName: "Sem.R. DA gr\u00fcn 02 A",
    address: "Freihausgeb\u00e4ude (Wiedner Hauptstr. 8)"
  },
  {
    roomName: "Sem.R. DA gr\u00fcn 02 B",
    address: "Freihausgeb\u00e4ude (Wiedner Hauptstr. 8)"
  },
  {
    roomName: "Sem.R. DA gr\u00fcn 02 C",
    address: "Wiedner Hauptstr. 8, , Turm A, gr\u00fcner Bereich, 2. OG"
  },
  {
    roomName: "FH H\u00f6rsaal 5 (FH5)",
    address: "Wiedner Hauptstr. 8, Turm A, gr\u00fcner Bereich, 2. OG"
  },
  {
    roomName: "FH H\u00f6rsaal 6 (FH6)",
    address: "Wiedner Hauptstr. 8, Turm A, gr\u00fcner Bereich, 2. OG"
  },
  {
    roomName: "Seminarraum DA gr\u00fcn 03 A",
    address: "Wiedner Hauptstr. 8, Turm A, gr\u00fcner Bereich, 3. OG"
  },
  {
    roomName: "Seminarraum DA gr\u00fcn 03 C",
    address: "Wiedner Hauptstr. 8, Turm A, gr\u00fcner Bereich, 3. OG"
  },
  {
    roomName: "Seminarraum DA gr\u00fcn 03 B",
    address: "Wiedner Hauptstr. 8, Turm A, gr\u00fcner Bereich, 3. OG"
  },
  {
    roomName: "Seminarraum DA gr\u00fcn 04",
    address: "Wiedner Hauptstr. 8, Turm A, gr\u00fcner Bereich, 4. OG"
  },
  {
    roomName: "Seminarraum DA gr\u00fcn 05",
    address: "Wiedner Hauptstr. 8, Turm A, gr\u00fcner Bereich, 5. OG"
  },
  {
    roomName: "Seminarraum DA gr\u00fcn 06A",
    address: "Wiedner Hauptstr. 8, Turm A, gr\u00fcner Bereich, 6. OG"
  },
  {
    roomName: "Seminarraum DA gr\u00fcn 06B",
    address: "Wiedner Hauptstr. 8, Turm A, gr\u00fcner Bereich, 6. OG"
  },
  {
    roomName: "Dissertantenzimmer DA gr\u00fcn 19B",
    address: "Wiedner Hauptstr. 8, Turm A, gr\u00fcner Bereich, 8. OG"
  },
  {
    roomName: "EDV Praktikum Physik",
    address: "Wiedner Hauptstr. 8, Turm A, gr\u00fcner Bereich, 1. OG"
  },
  {
    roomName: "Mensa",
    address: ""
  },
  {
    roomName: "FH H\u00f6rsaal 7 (FH7)",
    address: "1040 Wien, Wiedner Hauptstr. 8, Turm B, gelber Bereich, 2. OG"
  },
  {
    roomName: "FH 8 N\u00f6bauer HS",
    address: "Wiedner Hauptstr. 8, Turm B, gelber Bereich, 2. OG"
  },
  {
    roomName: "FH H\u00f6rsaal 4 (FH4)",
    address: "Wiedner Hauptstr. 8, Turm B, gelber Bereich, 2. OG"
  },
  {
    roomName: "FH H\u00f6rsaal 3 (FH3)",
    address: "Wiedner Hauptstr. 8, Turm B, gelber Bereich, 2. OG"
  },
  {
    roomName: "FH H\u00f6rsaal 2 (FH2)",
    address: "Wiedner Hauptstr. 8, Turm B, gelber Bereich, 2. OG"
  },
  {
    roomName: "Seminarraum DB gelb 03",
    address: "Wiedner Hauptstr. 8, Turm B, gelber Bereich, 3. OG"
  },
  {
    roomName: "Seminarraum DB gelb 04",
    address: "Wiedner Hauptstr. 8, Turm B, gelber Bereich, 4.OG"
  },
  {
    roomName: "Seminarraum DB gelb 05 A",
    address: "Wiedner Hauptstr. 8, Turm B, gelber Bereich, 5. OG"
  },
  {
    roomName: "Seminarraum DB gelb 05 B",
    address: "Wiedner Hauptstr. 8, Turm B, gelber Bereich, 5. OG"
  },
  {
    roomName: "Seminarraum DB gelb 07",
    address: "Wiedner Hauptstr. 8, Turm B, gelber Bereich, 7. OG"
  },
  {
    roomName: "Seminarraum DB gelb 09",
    address: "Wiedner Hauptstr. 8, Turm B, gelber Bereich, 9. OG"
  },
  {
    roomName: "Seminarraum DB gelb 10",
    address: "Wiedner Hauptstr. 8, Turm B, gelber Bereich, 10. OG"
  },
  {
    roomName: "HTU Lernraum Alte Mensa",
    address: "Wiedner Hauptstra\u00dfe 8-10, 1. Stock, Roter Bereich, 1040 Wien"
  },
  {
    roomName: "HTU-Gro\u00dfraum",
    address: ""
  },
  {
    roomName: "FH H\u00f6rsaal 1 (FH1)",
    address: "Wiedner Hauptstr. 8, Turm C, roter Bereich, 1. u. 2. OG"
  },
  {
    roomName: "Seminarraum DC rot 07",
    address: "Wiedner Hauptstr. 8, Turm C, roter Bereich, 7. OG"
  },
  {
    roomName: "Vortragsraum Bibliothek",
    address: "Resselgasse 4, 5. OG Bibliothek"
  },
  {
    roomName: "Fachschaft Wirtschaftsinformatik und Data Science",
    address: "Treitlstra\u00dfe 3, 1.Stock, gerade"
  },
  {
    roomName: "",
    address: "1040 Wien, Treitlstra\u00dfe 3; 2. OG; Plan des Stockwerks."
  },
  {
    roomName: "",
    address: "1040 Wien, Treitlstra\u00dfe 3; 2. OG;"
  },
  {
    roomName: "",
    address: "1040 Wien, Treitlstra\u00dfe 3; 3. OG;"
  },
  {
    roomName: "",
    address: "1040 Wien, Treitlstra\u00dfe 3; 3. OG;"
  },
  {
    roomName: "",
    address: "1040 Wien, Treitlstra\u00dfe 3; 3. OG"
  },
  {
    roomName: "",
    address: "Treitlstra\u00dfe 3, 3. Stock"
  },
  {
    roomName: "",
    address: "1040 Wien, Treitlstra\u00dfe 3; 4. OG;"
  },
  {
    roomName: "",
    address: "1040 Wien, Treitlstra\u00dfe 3; 4. OG;"
  },
  {
    roomName: "",
    address: "1040 Wien, Treitlstra\u00dfe 3; 4. OG;"
  },
  {
    roomName: "Seminarraum IFM (EDV Raum)",
    address: "Treitlstra\u00dfe 3, Bauteil DE, Dachgeschoss"
  },
  {
    roomName: "Seminarraum Techn. Informatik",
    address: "Treitlstra\u00dfe 3, Zugang \u00fcber Operngasse 9 (Erdgescho\u00df)"
  },
  {
    roomName: "Informatikh\u00f6rsaal",
    address: "Treitlstra\u00dfe 3, Erdgeschoss"
  },
  {
    roomName: "Lernraum 2, Fachschaft Informatik",
    address: "1040 Wien, Treitlstra\u00dfe 3, Hochparterre"
  },
  {
    roomName: "Anita-Borg Raum (Beratungsraum), Fachschaft Informatik",
    address: "1040 Wien, Treitlstra\u00dfe 3, Hochparterre"
  },
  {
    roomName: "Lernraum 1, Fachschaft Informatik",
    address: "1040 Wien, Treitlstra\u00dfe 3, Hochparterre"
  },
  {
    roomName: "Seminarraum 1/3 OPG",
    address: "Operngasse 11, Operngasse 11 / 3. Stock"
  },
  {
    roomName: "",
    address: "Argentinierstrasse 8, 2. Stock"
  },
  {
    roomName: "",
    address: "1040 Wien, Argentinierstra\u00dfe 8; geradeaus; ins 2. OG; Gangmitte;"
  },
  {
    roomName: "",
    address: "1040 Wien, Argentinierstra\u00dfe 8; geradeaus; ins 2. OG; im Gang rechts"
  },
  {
    roomName: "",
    address: "1040 Wien, Argentinierstra\u00dfe 8; geradeaus; ins 3. OG; Gangmitte;"
  },
  {
    roomName: "",
    address: "1040 Wien, Argentinierstra\u00dfe 8; geradeaus; ins 3. OG; Gangmitte"
  },
  {
    roomName: "",
    address: "1040 Wien, Argentinierstra\u00dfe 8; geradeaus; ins 3. OG; Gangmitte;"
  },
  {
    roomName: "",
    address: "1040 Wien, Argentinierstra\u00dfe 8; geradeaus; ins 4. OG; Gangmitte;"
  },
  {
    roomName: "",
    address: "1040 Wien, Argentinierstra\u00dfe 8; geradeaus; ins 4. OG; Gangmitte;"
  },
  {
    roomName: "",
    address: "1040 Wien, Argentinierstra\u00dfe 8; geradeaus; ins 4. OG; Gangmitte;"
  },
  {
    roomName: "",
    address: "1040 Wien, Argentinierstra\u00dfe 8; geradeaus; ins 4. OG; Gangmitte;"
  },
  {
    roomName: "",
    address: "1040 Wien, Argentinierstra\u00dfe 8; geradeaus; ins 4. OG; Gangmitte;"
  },
  {
    roomName: "Besprechungsraum Kuppel",
    address: "1040 Wien, Argentinierstra\u00dfe 8; im EG rechts: Besprechungsraum Kuppel;"
  },
  {
    roomName: "Seminarraum Argentinierstrasse",
    address: "Argentinierstr. 8, Argentinierstrasse 8 od. Paniglgasse, EG"
  },
  {
    roomName: "DSLab",
    address: "Argentinierstrasse 8, Karlsgasse 11-13, Basement"
  },
  {
    roomName: "Besprechungsraum Galerie",
    address: "Argentinierstrasse 8, 1. Stock"
  },
  {
    roomName: "Seminarraum 1",
    address: "Karlsgasse 11, Hochparterre links"
  },
  {
    roomName: "Seminarraum 2",
    address: "Karlsgasse 11, Hochparterre rechts"
  },
  {
    roomName: "Seminarraum 264/1",
    address: "Karlsgasse 13, 1. Stock"
  },
  {
    roomName: "Seminarraum 268/2",
    address: "Karlsgasse 13, 1. Stock"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 2. OG; vom Lift aus links den 2. Gang nehmen (Verlauf folgen);"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 2. OG; vom Lift aus links den 2. Gang nehmen (Verlauf folgen);"
  },
  {
    roomName: "Seminarraum 183/2",
    address: "Favoritenstr. 9-11, gelber Bereich, Stiege 1"
  },
  {
    roomName: "Seminarraum 186",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege I; ins 5. OG; Seminarraum links;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege II; ins 2. OG; vom Lift aus links;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege II; ins 2. OG; vom Lift aus links;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege I; ins 3. OG; rechts: Gegensprechanlage Klappe 18542 w\u00e4hlen;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; Durch Gittertor und Hof zu Stiege II; ins 3. OG; Gegensprechanlage die Nummer 'B18541' w\u00e4hlen;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege I; ins 3. OG; rechts: Gegensprechanlage Klappe 18543 w\u00e4hlen;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege I; ins 3. OG; rechts: Gegensprechanlage Klappe 18544 w\u00e4hlen;"
  },
  {
    roomName: "Seminarraum G\u00f6del",
    address: "Favoritenstr. 9-11, EG, Zugang vom Innenhof"
  },
  {
    roomName: "Seminarraum von Neumann",
    address: "Favoritenstr. 9-11, EG, Zugang vom Innenhof"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege II; ins 2. OG; vom Lift aus rechts;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege II; ins 2. OG; vom Lift aus rechts;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege II; ins 2. OG; vom Lift aus rechts;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege II; ins 3. OG; vom Lift aus links;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 3. OG; vom Lift aus gerade zur Glast\u00fcr und dort l\u00e4uten;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege II; ins 4. OG; vom Lift aus links;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege II; ins 4. OG; vom Lift aus rechts;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege II; ins 5. OG"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege II; ins 5. OG;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege II; ins 5. OG;"
  },
  {
    roomName: "",
    address: "Favoritenstrasse, Stiege 3, 1. Stock"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 2. OG.; vom Lift aus rechts den 2. Gang nehmen, 3. T\u00fcre links;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 2. OG.; vom Lift aus links den 2. Gang nehmen, 1. T\u00fcre rechts;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 4. OG.; vom Lift aus rechts den 2. Gang nehmen, 3. T\u00fcre links;"
  },
  {
    roomName: "Seminarraum 188/2",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 4. OG.; vom Lift aus rechts den 2. Gang nehmen, 1. T\u00fcre links: Seminarraum 188/2;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 4. OG.; vom Lift aus geradeaus: Studierstube-Labor; Plan des Stockwerks."
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor und Hof zu Stiege II; ins 5. OG;"
  },
  {
    roomName: "Seminarraum FAV 01 A (Seminarraum 183/2)",
    address: "Favoritenstr. 9-11, EG"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 2. OG; vom Lift aus links durch Glasgang;"
  },
  {
    roomName: "Informatiklabor Breakout",
    address: "Favoritenstr. 9-11, 2.OG(Zugang \u00fcber Stiege 4)"
  },
  {
    roomName: "Informatiklabor Zelda",
    address: "Favoritenstr. 9-11, 2.OG (Zugang \u00fcber Stiege 4)"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 11; direkt rechts neben dem Eingang zu Stiege IV; ins 2. OG; dem Gang folgen (der Gang biegt nach links);"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 3. OG; vom Lift aus gerade zur Glast\u00fcr und dort l\u00e4uten;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 3. OG; vom Lift aus gerade zur Glast\u00fcr und dort l\u00e4uten;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 4. OG.; vom Lift aus rechts den 1., leicht ansteigenden Gang nehmen; danach rechts; dem Gang weiter folgen; Plan des Stockwerks."
  },
  {
    roomName: "FAV H\u00f6rsaal 1",
    address: "Favoritenstr. 9-11, Erdgescho\u00df"
  },
  {
    roomName: "FAV H\u00f6rsaal 2",
    address: "Favoritenstr. 9-11, Erdgescho\u00df"
  },
  {
    roomName: "ehem. Breakout-Raum, ist jetzt im 2. Stock",
    address: "Beim Eingang Favoritenstra\u00dfe 11 rein gehen und direkt nach links abbiegen. Der Raum ist die erste T\u00fcr links."
  },
  {
    roomName: "B\u00fcro der Inf-Lab Techniker",
    address: "1040 Wien, Favoritenstra\u00dfe 11; gerade; vor Labor rechts, dann links Richtung Ausgang: letzte T\u00fcre rechts"
  },
  {
    roomName: "Seminarraum 187/2",
    address: "Favoritenstr. 9-11, Blauer Bereich, Stiege 3"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 2. OG; vom Lift aus rechts den 1., leicht ansteigenden Gang nehmen;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 3. OG; vom Lift aus gerade zur Glast\u00fcr und dort l\u00e4uten;"
  },
  {
    roomName: "Conference room Hahn",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 3. OG; vom Lift aus gerade zur Glast\u00fcr und dort l\u00e4uten;"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 3. OG; vom Lift aus gerade zur Glast\u00fcr und dort l\u00e4uten;"
  },
  {
    roomName: "B\u00fcro von Horst Eidenberger",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 4. OG; vom Lift aus rechts den 1., leicht ansteigenden Gang nehmen; danach rechts"
  },
  {
    roomName: "B\u00fcro von Martin Kampel",
    address: "1040 Wien, Favoritenstra\u00dfe 9; durch Gittertor danach rechts durch (automatische) Glast\u00fcre zu Stiege III; gerade; ins 4. OG; vom Lift aus rechts den 1., leicht ansteigenden Gang nehmen; danach rechts;"
  },
  {
    roomName: "B\u00fcro von Robert Sablatnig",
    address: "Favoritenstra\u00dfe, 4. Stock. Um hinzukommen, muss man die Stiege 3 (beim Eingang nach links) raufgehen und dann nach rechts."
  },
  {
    roomName: "Informatiklabor Q*bert",
    address: "Favoritenstr. 9-11, Erdgescho\u00df"
  },
  {
    roomName: "Informatiklabor Frogger",
    address: "Favoritenstr. 9-11, Erdgescho\u00df"
  },
  {
    roomName: "Informatiklabor Pong",
    address: "Favoritenstr. 9-11, Erdgescho\u00df"
  },
  {
    roomName: "Seminarraum Zemanek",
    address: "Favoritenstr. 9-11, hellgr\u00fcner Bereich, Stiege 3"
  },
  {
    roomName: "",
    address: "1040 Wien, Favoritenstra\u00dfe 16; 3. OG;"
  },
  {
    roomName: "Seminarraum 233",
    address: "Gu\u00dfhausstr. 28-30"
  },
  {
    roomName: "Seminarraum 126",
    address: "Gu\u00dfhausstr. 28-30,"
  },
  {
    roomName: "",
    address: "1040 Wien, Erzherzog-Johann-Platz 1; (automatische T\u00fcr); ins 4. OG; vom Lift aus links;"
  },
  {
    roomName: "Seminarraum 362",
    address: "Floragasse 7,"
  },
  {
    roomName: "Seminarraum 362/1",
    address: "Floragasse 7, Floragasse 7, 1. Stock"
  },
  {
    roomName: "Seminarraum 366-MST",
    address: "Floragasse 7, 2. Stock"
  },
  {
    roomName: "",
    address: "1040 Wien, Floragasse 7; ins 7. OG.;"
  },
  {
    roomName: "Seminarraum Funke Halle",
    address: "Adolf Blamauerg. 1-3,"
  },
  {
    roomName: "Bombardier H\u00f6rsaal",
    address: "Theresianumgasse 27"
  },
  {
    roomName: "Theresianumgasse HS 2",
    address: "Teresianumgasse 27"
  }
]

export interface Shorthand {
  ft: string,
  a: string
}

export const shorthands: Shorthand[] = [
  {
    ft: "VU Unification Theory",
    a: "VU UT"
  },
  {
    ft: "VU Similarity Modeling 2",
    a: "VU SM2"
  },
  {
    ft: "VU Semantik von Programmiersprachen",
    a: "VU SvP"
  },
  {
    ft: "VU Software Maintenance and Evolution",
    a: "VU SME"
  },
  {
    ft: "VU Advanced Object Oriented Programming",
    a: "VU AOOP"
  },
  {
    ft: "VU Algorithmic Meta Theorems Algorithmische Meta Theoreme",
    a: "VU AMTAMT"
  },
  {
    ft: "PR Project in Computer Science 1",
    a: "PR PCS1"
  },
  {
    ft: "VU Practical Applications of Answer Set Programming",
    a: "VU PAoASP"
  },
  {
    ft: "VU Enterprise Resource Planning and Control",
    a: "VU ERPC"
  },
  {
    ft: "VU Peer to Peer Systems",
    a: "VU PtPS"
  },
  {
    ft: "UE Network Engineering",
    a: "UE NE"
  },
  {
    ft: "VU Rigorous Systems Engineering",
    a: "VU RSE"
  },
  {
    ft: "VU GPU Architectures and Computing",
    a: "VU GAC"
  },
  {
    ft: "VU Value Based Software Engineering",
    a: "VU VBSE"
  },
  {
    ft: "VU Quantum Computing",
    a: "VU QC"
  },
  {
    ft: "SE Seminar in Logic",
    a: "SE SL"
  },
  {
    ft: "SE VWA Mentoring I",
    a: "SE VMI"
  },
  {
    ft: "PR Project in Computer Science 1",
    a: "PR PCS1"
  },
  {
    ft: "VU Distributed Systems Engineering",
    a: "VU DSE"
  },
  {
    ft: "VU Einf\u00fchrung in Semantic Systems",
    a: "VU ESS"
  },
  {
    ft: "VU Distributed Systems Technologies",
    a: "VU DST"
  },
  {
    ft: "VO Analysis of Algorithms",
    a: "VO AoA"
  },
  {
    ft: "VU Cryptocurrencies",
    a: "VU Cryptocurrencies"
  },
  {
    ft: "SE Kommunikation und Rhetorik",
    a: "SE KR"
  },
  {
    ft: "VU Serverless Computing",
    a: "VU SC"
  },
  {
    ft: "VU Preferences in Artificial Intelligence",
    a: "VU PAI"
  },
  {
    ft: "PR Project in Computer Science 1",
    a: "PR PCS1"
  },
  {
    ft: "VU Techniksoziologie und Technikpsychologie",
    a: "VU TT"
  },
  {
    ft: "VU Management von Software Projekten",
    a: "VU MvSP"
  },
  {
    ft: "VU Automated Deduction",
    a: "VU AD"
  },
  {
    ft: "VU Efficient Algorithms",
    a: "VU EA"
  },
  {
    ft: "SE Kommunikationstechnik",
    a: "SE Kommunikationstechnik"
  },
  {
    ft: "VU Software Quality Management",
    a: "VU SQM"
  },
  {
    ft: "VU Business Intelligence",
    a: "VU BI"
  },
  {
    ft: "VU Software Model Checking",
    a: "VU SMC"
  },
  {
    ft: "VU Financial Management and Reporting",
    a: "VU FMR"
  },
  {
    ft: "VU Advanced Software Engineering",
    a: "VU ASE"
  },
  {
    ft: "VU Real Time Scheduling",
    a: "VU RTS"
  },
  {
    ft: "VU Smart Contracts",
    a: "VU SC"
  },
  {
    ft: "VU Mobile Robotik",
    a: "VU MR"
  },
  {
    ft: "VU Semi Automatic Information and Knowledge Systems",
    a: "VU SAIKS"
  },
  {
    ft: "VU Optimierende \u00dcbersetzer",
    a: "VU O\u00dc"
  },
  {
    ft: "VO Deduktive Datenbanken",
    a: "VO DD"
  },
  {
    ft: "VU Algorithmic Social Choice",
    a: "VU ASC"
  },
  {
    ft: "VU Randomized Algorithms",
    a: "VU RA"
  },
  {
    ft: "VU Hybrid Classic Quantum Systems",
    a: "VU HCQS"
  },
  {
    ft: "VU Service Level Agreements",
    a: "VU SLA"
  },
  {
    ft: "VU Computability Theory",
    a: "VU CT"
  },
  {
    ft: "VU Pr\u00e4sentations und Verhandlungstechnik",
    a: "VU PV"
  },
  {
    ft: "VU Fortgeschrittene logische Programmierung",
    a: "VU FlP"
  },
  {
    ft: "VU Hands On Cloud Native",
    a: "VU HOCN"
  },
  {
    ft: "UE Algorithmic Geometry",
    a: "UE AG"
  },
  {
    ft: "VU Knowledge Graphs",
    a: "VU KG"
  },
  {
    ft: "VU Digitale Nachhaltigkeit",
    a: "VU DN"
  },
  {
    ft: "VO Pr\u00e4sentation Moderation und Mediation",
    a: "VO PMM"
  },
  {
    ft: "SE Seminar in Software Engineering",
    a: "SE SSE"
  },
  {
    ft: "VU Advanced Project Management",
    a: "VU APM"
  },
  {
    ft: "VU Complexity Theory",
    a: "VU CT"
  },
  {
    ft: "SE Kommunikation und Rhetorik 2",
    a: "SE KR2"
  },
  {
    ft: "VU Deductive Verification of Software",
    a: "VU DVoS"
  },
  {
    ft: "VU Rigorous Systems Engineering",
    a: "VU RSE"
  },
  {
    ft: "UE Knowledge Management",
    a: "UE KM"
  },
  {
    ft: "VO Frauen in Naturwissenschaft und Technik",
    a: "VO FNT"
  },
  {
    ft: "UE Formale Methoden der Informatik",
    a: "UE FMI"
  },
  {
    ft: "VU Verteiltes Programmieren mit Space Based Computing Middleware",
    a: "VU VPmSBCM"
  },
  {
    ft: "VU Web Application Engineering and Content Management",
    a: "VU WAECM"
  },
  {
    ft: "PR Project in Computer Science 1",
    a: "PR PCS1"
  },
  {
    ft: "VU Advanced Topics in Formal Language Theory",
    a: "VU ATFLT"
  },
  {
    ft: "SE Privatissimum aus Fachdidaktik Informatik",
    a: "SE PaFI"
  },
  {
    ft: "VU Rhetorik K\u00f6rpersprache Argumentationstraining",
    a: "VU RKA"
  },
  {
    ft: "VO Inductive Rule Learning",
    a: "VO IRL"
  },
  {
    ft: "VU Software Testing",
    a: "VU ST"
  },
  {
    ft: "VU Graph Drawing Algorithms",
    a: "VU GDA"
  },
  {
    ft: "SE Seminar f\u00fcr Diplomand innen",
    a: "SE SDi"
  },
  {
    ft: "VU Datenbanktheorie",
    a: "VU Datenbanktheorie"
  },
  {
    ft: "VU Algorithmic Geometry",
    a: "VU AG"
  },
  {
    ft: "VO Typsysteme",
    a: "VO Typsysteme"
  },
  {
    ft: "VU Sicherheit Privacy und Erkl\u00e4rbarkeit in Maschinellem Lernen",
    a: "VU SPEML"
  },
  {
    ft: "VU Programming Principles of Mobile Robotics",
    a: "VU PPoMR"
  },
  {
    ft: "VU Kooperatives Arbeiten",
    a: "VU KA"
  },
  {
    ft: "VU Kommunikation und Moderation",
    a: "VU KM"
  },
  {
    ft: "VU Large scale Distributed Computing",
    a: "VU LsDC"
  },
  {
    ft: "VU Machine Learning",
    a: "VU ML"
  },
  {
    ft: "VU Probabilistic Programming and AI",
    a: "VU PPA"
  },
  {
    ft: "VU Applied Web Data Extraction and Integration",
    a: "VU AWDEI"
  },
  {
    ft: "VU Programming Principles of Mobile Robotics II",
    a: "VU PPoMRI"
  },
  {
    ft: "VU Model Engineering",
    a: "VU ME"
  },
  {
    ft: "VU Structural Decompositions and Algorithms",
    a: "VU SDA"
  },
  {
    ft: "PR Project in Computer Science 2",
    a: "PR PCS2"
  },
  {
    ft: "VU Algorithmics",
    a: "VU Algorithmics"
  },
  {
    ft: "SE Seminar in Distributed Systems",
    a: "SE SDS"
  },
  {
    ft: "VU Mathematical Programming",
    a: "VU MP"
  },
  {
    ft: "VU \u00dcbersetzer f\u00fcr Parallele Systeme",
    a: "VU \u00dcPS"
  },
  {
    ft: "VU Programmanalyse",
    a: "VU Programmanalyse"
  },
  {
    ft: "VU Advanced Multiprocessor Programming",
    a: "VU AMP"
  },
  {
    ft: "UE Data Stewardship",
    a: "UE DS"
  },
  {
    ft: "VU Algorithms Design",
    a: "VU AD"
  },
  {
    ft: "VU Advanced Model Engineering",
    a: "VU AME"
  },
  {
    ft: "VU Problem Solving and Search in Artificial Intelligence",
    a: "VU PSSAI"
  },
  {
    ft: "VU Programming Principles of Mobile Robotics",
    a: "VU PPoMR"
  },
  {
    ft: "VO Data Stewardship",
    a: "VO DS"
  },
  {
    ft: "VU Enterprise Risk Management Basics",
    a: "VU ERMB"
  },
  {
    ft: "SE Seminar aus \u00dcbersetzerbau",
    a: "SE Sa\u00dc"
  },
  {
    ft: "VU Theoretical Foundations and Research Topics in Machine Learning",
    a: "VU TFRTML"
  },
  {
    ft: "VU Crypto Asset Analytics",
    a: "VU CAA"
  },
  {
    ft: "VU Grundlagen des Information Retrieval",
    a: "VU GdIR"
  },
  {
    ft: "VU Media and Brain 1",
    a: "VU MB1"
  },
  {
    ft: "VU Workflow Modeling and Process Management",
    a: "VU WMPM"
  },
  {
    ft: "VU Analyse und Verifikation",
    a: "VU AV"
  },
  {
    ft: "VU Optimization in Transport and Logistics",
    a: "VU OTL"
  },
  {
    ft: "VU Dynamic Compilation",
    a: "VU DC"
  },
  {
    ft: "VU Kryptographie",
    a: "VU Kryptographie"
  },
  {
    ft: "UE Formale Methoden der Informatik",
    a: "UE FMI"
  },
  {
    ft: "VU Molecular Computing",
    a: "VU MC"
  },
  {
    ft: "VU Risk Management",
    a: "VU RM"
  },
  {
    ft: "VO Einf\u00fchrung in die Wissenschaftstheorie I",
    a: "VO EWI"
  },
  {
    ft: "VU Softskills f\u00fcr TechnikerInnen",
    a: "VU ST"
  },
  {
    ft: "VU Modeling and Solving Constrained Optimization Problems",
    a: "VU MSCOP"
  },
  {
    ft: "VU Problems in Distributed Computing",
    a: "VU PDC"
  },
  {
    ft: "SE Seminar aus Algorithmik",
    a: "SE SaA"
  },
  {
    ft: "VO Einf\u00fchrung in Technik und Gesellschaft",
    a: "VO ETG"
  },
  {
    ft: "SE Coaching als F\u00fchrungsinstrument 1",
    a: "SE CaF1"
  },
  {
    ft: "SE Advanced Model Engineering",
    a: "SE AME"
  },
  {
    ft: "VU Project and Enterprise Financing",
    a: "VU PEF"
  },
  {
    ft: "VU Formal Methods for Concurrent and Distributed Systems",
    a: "VU FMCDS"
  },
  {
    ft: "VU Programmiersprachen",
    a: "VU Programmiersprachen"
  },
  {
    ft: "VU Advanced Information Retrieval",
    a: "VU AIR"
  },
  {
    ft: "VU Advanced Distributed Systems",
    a: "VU ADS"
  },
  {
    ft: "VU Dependable Distributed Systems",
    a: "VU DDS"
  },
  {
    ft: "VU Selbstorganisierende Systeme",
    a: "VU SS"
  },
  {
    ft: "SE Coaching als F\u00fchrungsinstrument 2",
    a: "SE CaF2"
  },
  {
    ft: "VU Software in Kommunikationsnetzen",
    a: "VU SK"
  },
  {
    ft: "VU Web Data Extracion and Integration",
    a: "VU WDEI"
  },
  {
    ft: "VU Foundations of Information Integration",
    a: "VU FoII"
  },
  {
    ft: "VU Systems and Applications Security",
    a: "VU SAS"
  },
  {
    ft: "VU e Business Modeling",
    a: "VU eBM"
  },
  {
    ft: "VU Parallele und Echtzeitprogrammierung",
    a: "VU PE"
  },
  {
    ft: "VU Automated Reasoning and Program Verification",
    a: "VU ARPV"
  },
  {
    ft: "VU Parallele Algorithmen",
    a: "VU PA"
  },
  {
    ft: "VU Wireless in Automation",
    a: "VU WA"
  },
  {
    ft: "VU GIS Theorie I",
    a: "VU GTI"
  },
  {
    ft: "PR Project in Computer Science 2",
    a: "PR PCS2"
  },
  {
    ft: "UE Computer Aided Verification",
    a: "UE CAV"
  },
  {
    ft: "VU Introduction to Type Theories",
    a: "VU ItTT"
  },
  {
    ft: "PR Project in Computer Science 2",
    a: "PR PCS2"
  },
  {
    ft: "VO Codegeneratoren",
    a: "VO Codegeneratoren"
  },
  {
    ft: "VU Distributed Algorithms",
    a: "VU DA"
  },
  {
    ft: "PR Advanced Software Engineering",
    a: "PR ASE"
  },
  {
    ft: "VO Theorie und Praxis der Gruppenarbeit",
    a: "VO TPG"
  },
  {
    ft: "VU High Performance Computing",
    a: "VU HPC"
  },
  {
    ft: "SE Seminar in Theoretical Computer Science",
    a: "SE STCS"
  },
  {
    ft: "UE Pr\u00e4sentation Moderation und Mediation",
    a: "UE PMM"
  },
  {
    ft: "VU Network Security Advanced Topics",
    a: "VU NSAT"
  },
  {
    ft: "VU Algorithms in Graph Theory",
    a: "VU AGT"
  },
  {
    ft: "VO EDV Vertragsrecht",
    a: "VO EV"
  },
  {
    ft: "VU Einf\u00fchrung in Semantic Systems",
    a: "VU ESS"
  },
  {
    ft: "SE Seminar aus Programmiersprachen",
    a: "SE SaP"
  },
  {
    ft: "VU Digital Forensics",
    a: "VU DF"
  },
  {
    ft: "VU Similarity Modeling 1",
    a: "VU SM1"
  },
  {
    ft: "VU Computational Equational Logic",
    a: "VU CEL"
  },
  {
    ft: "VO Zwischen Karriere und Barriere",
    a: "VO ZKB"
  },
  {
    ft: "VU Computer Aided Verification",
    a: "VU CAV"
  },
  {
    ft: "VU Methods of Empirical Software Engineering",
    a: "VU MoESE"
  },
  {
    ft: "VU Heuristic Optimization Techniques",
    a: "VU HOT"
  },
  {
    ft: "VU Advanced Internet Computing",
    a: "VU AIC"
  },
  {
    ft: "PR Project in Computer Science 2",
    a: "PR PCS2"
  },
  {
    ft: "UE Analysis of Algorithms",
    a: "UE AoA"
  },
  {
    ft: "VU Term Rewriting",
    a: "VU TR"
  },
  {
    ft: "VU Formal Methods in Computer Science",
    a: "VU FMCS"
  },
  {
    ft: "VU IT Security in Large IT Infrastructures",
    a: "VU ISLII"
  },
  {
    ft: "VU Network Security",
    a: "VU NS"
  },
  {
    ft: "VU Coalgebra in Computer Science",
    a: "VU CCS"
  },
  {
    ft: "VU Risk Model Management",
    a: "VU RMM"
  },
  {
    ft: "SE Seminar aus Datenbanken",
    a: "SE SaD"
  },
  {
    ft: "VU Computer Networks",
    a: "VU CN"
  },
  {
    ft: "SE Gruppendynamik",
    a: "SE Gruppendynamik"
  },
  {
    ft: "VU Stackbasierte Sprachen",
    a: "VU SS"
  },
  {
    ft: "VU Advanced Cryptography",
    a: "VU AC"
  },
  {
    ft: "VO Pervasive and Mobile Computing",
    a: "VO PMC"
  },
  {
    ft: "SE Didaktik in der Informatik",
    a: "SE DI"
  },
  {
    ft: "VU Networks Design and Analysis",
    a: "VU NDA"
  },
  {
    ft: "VU Forschungsmethoden",
    a: "VU Forschungsmethoden"
  },
  {
    ft: "VU Fortgeschrittene funktionale Programmierung",
    a: "VU FfP"
  },
  {
    ft: "PR Project in Computer Science 1",
    a: "PR PCS1"
  },
  {
    ft: "VU Configuration Management",
    a: "VU CM"
  },
  {
    ft: "SE Seminar in Formal Methods",
    a: "SE SFM"
  },
  {
    ft: "VO GIS Theorie II",
    a: "VO GTI"
  },
  {
    ft: "VU Requirements Engineering and Specification",
    a: "VU RES"
  },
  {
    ft: "SE VWA Mentoring II",
    a: "SE VMI"
  },
  {
    ft: "VU Advanced Topics in Theoretical Computer Science",
    a: "VU ATTCS"
  },
  {
    ft: "VU SAT Solving and Extensions",
    a: "VU SSE"
  },
  {
    ft: "VU Effiziente Programme",
    a: "VU EP"
  },
  {
    ft: "VO Knowledge Management",
    a: "VO KM"
  },
  {
    ft: "VU Software Architecture",
    a: "VU SA"
  },
  {
    ft: "SE Seminar aus Security",
    a: "SE SaS"
  },
  {
    ft: "VU Media and Brain 2",
    a: "VU MB2"
  },
  {
    ft: "VU Advanced Security for Systems Engineering",
    a: "VU ASSE"
  },
  {
    ft: "VO Grundlagen der Makro\u00f6konomie",
    a: "VO GM"
  },
  {
    ft: "VU Pr\u00e4sentation und Moderation",
    a: "VU PM"
  },
  {
    ft: "VU Mobile Network Services and Applications",
    a: "VU MNSA"
  },
  {
    ft: "VU Formal Language Theory",
    a: "VU FLT"
  },
  {
    ft: "VU Formal Methods for Security and Privacy",
    a: "VU FMSP"
  },
  {
    ft: "VU Fixed Parameter Algorithms and Complexity",
    a: "VU FPAC"
  },
  {
    ft: "VU IT based Management",
    a: "VU IbM"
  },
  {
    ft: "VU Discrete Reasoning Methods",
    a: "VU DRM"
  },
  {
    ft: "SE Folgenabsch\u00e4tzung von Informationstechnologien",
    a: "SE FvI"
  },
  {
    ft: "VU Information Design",
    a: "VU ID"
  },
  {
    ft: "VU Advanced Algorithms",
    a: "VU AA"
  },
  {
    ft: "VU Approximation Algorithms",
    a: "VU AA"
  },
  {
    ft: "VU Datenbanksysteme Vertiefung",
    a: "VU DV"
  },
  {
    ft: "VO Network Engineering",
    a: "VO NE"
  },
  {
    ft: "VU Membrane Computing",
    a: "VU MC"
  },
  {
    ft: "PR Project in Computer Science 2",
    a: "PR PCS2"
  }
]

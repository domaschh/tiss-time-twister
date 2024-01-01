import {EventColor} from "calendar-utils";

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

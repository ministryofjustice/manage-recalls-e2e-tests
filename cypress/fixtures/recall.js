import { DateTime } from "luxon";

const tomorrow = DateTime.now().plus({ days: 1 });
const sixMonthsTime = tomorrow.plus({ months: 6 });
const nineMonthsTime = tomorrow.plus({ months: 9 });

export const recall = {
  recallType: "Fixed term",
  agreeWithRecall: "YES",
  agreeWithRecallDetail: "Reasons...",
  previousConvictionMainName: "Walter Holt",
  licenceNameCategory: "FIRST_LAST",
  previousConvictionMainNameCategory: "FIRST_LAST",
  recallEmailReceivedDateTime: "2020-12-05T15:33:57.000Z",
  lastReleasePrison: "KTI",
  lastReleasePrisonLabel: "Kennet (HMP)",
  sentenceDate: "2019-08-03",
  sentenceExpiryDate: tomorrow.toISODate(),
  licenceExpiryDate: sixMonthsTime.toISODate(),
  conditionalReleaseDate: nineMonthsTime.toISODate(),
  lastReleaseDate: "2020-08-03",
  localPoliceForceId: "devon-and-cornwall",
  localPoliceForceLabel: "Devon & Cornwall Police",
  sentencingCourt: "ABDRCT",
  sentencingCourtLabel: "Aberdare County Court",
  indexOffence: "Burglary",
  contraband: true,
  contrabandDetail: "Intention to smuggle drugs",
  vulnerabilityDiversity: true,
  vulnerabilityDiversityDetail: "Various...",
  arrestIssues: true,
  arrestIssuesDetail: "Detail...",
  mappaLevel: "LEVEL_1",
  mappaLevelLabel: "Level 1",
  sentenceLength: {
    years: 2,
    months: 3,
    days: 0,
  },
  lastKnownAddressOption: "YES",
  lastKnownAddresses: [
    {
      fullAddress: "1, LYNN ROAD, WALTON HIGHWAY, WISBECH, PE14 7DF",
      line1: "1, LYNN ROAD",
      town: "WISBECH",
      postcode: "PE14 7DF",
    },
    {
      line1: "345 Porchester Road",
      line2: "Southsea",
      town: "Portsmouth",
      postcode: "PO1 4OY",
    },
  ],
  bookingNumber: "12345C",
  probationOfficerName: "Dave Angel",
  probationOfficerPhoneNumber: "07473739388",
  probationOfficerEmail: "probation.office@justice.com",
  localDeliveryUnit: "CENTRAL_AUDIT_TEAM",
  localDeliveryUnitLabel: "Central Audit Team",
  authorisingAssistantChiefOfficer: "Bob Monkfish",
  licenceConditionsBreached: "(i) one\n(ii) two",
  reasonsForRecall: ["OTHER", "ELM_FAILURE_CHARGE_BATTERY"],
  reasonsForRecallOtherDetail: "other reason detail...",
  currentPrison: "BFI",
  currentPrisonLabel: "Bedford (HMP)",
  additionalLicenceConditions: true,
  additionalLicenceConditionsDetail: "one, two",
  differentNomsNumber: true,
  differentNomsNumberDetail: "A1234AA",
  recallNotificationEmailSentDateTime: "2021-08-15T13:04:00.000Z",
  dossierEmailSentDate: "2021-09-08",
  hasDossierBeenChecked: true,
  recallAssessmentDueDateTime: "2020-08-06T15:33:57.000Z",
  dossierTargetDate: tomorrow.toISODate(),
  warrantReferenceNumber: "04RC/6457367A74325",
};

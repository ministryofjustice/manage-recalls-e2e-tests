import { DateTime } from 'luxon';

const tomorrow = DateTime.now().plus({ days: 1 });
const yesterday = DateTime.now().minus({ days: 1 });
const twoDaysAgo = DateTime.now().minus({ days: 2 });
const sixMonthsTime = DateTime.now().plus({ months: 6 });
const nineMonthsTime = DateTime.now().plus({ months: 9 });
const fourYearsAgo = DateTime.now().minus({ years: 4 });
const twoYearsAgo = DateTime.now().minus({ years: 2 });

export const recall = {
  recallType: 'Fixed term',
  confirmedRecallTypeDetail: 'Reasons...',
  previousConvictionMainName: 'Walter Holt',
  licenceNameCategory: 'FIRST_LAST',
  previousConvictionMainNameCategory: 'FIRST_LAST',
  recallEmailReceivedDateTime: yesterday.toISO(),
  lastReleasePrison: 'KTI',
  lastReleasePrisonLabel: 'Kennet (HMP)',
  sentenceDate: fourYearsAgo.toISODate(),
  sentenceExpiryDate: tomorrow.toISODate(),
  licenceExpiryDate: sixMonthsTime.toISODate(),
  conditionalReleaseDate: nineMonthsTime.toISODate(),
  lastReleaseDate: twoYearsAgo.toISODate(),
  localPoliceForceId: 'devon-and-cornwall',
  localPoliceForceLabel: 'Devon & Cornwall Police',
  sentencingCourt: 'ABDRCT',
  sentencingCourtLabel: 'Aberdare County Court',
  indexOffence: 'Burglary',
  contraband: true,
  contrabandDetail: 'Intention to smuggle drugs',
  vulnerabilityDiversity: true,
  vulnerabilityDiversityDetail: 'Various...',
  arrestIssues: true,
  arrestIssuesDetail: 'Detail...',
  mappaLevel: 'LEVEL_1',
  mappaLevelLabel: 'Level 1',
  sentenceLength: {
    years: 2,
    months: 3,
    days: 0,
  },
  notes: [
    {
      noteId: '678',
      subject: 'another note subject',
      details: 'another note, this one with document',
      index: 3,
      documentId: '834',
      fileName: 'my-note-attachment.doc',
      createdByUserName: 'Bobby Badger',
      createdDateTime: '2021-03-12T12:24:03.000Z'
    }
  ],
  partBRecords: [
    {
      partBRecordId: '123',
      details: 'Part B received',
      partBReceivedDate: '2022-03-05',
      version: 1,
      partBDocumentId: '456',
      partBFileName: '2022 part B.pdf',
      emailId: '789',
      emailFileName: 'part B.msg',
      oasysDocumentId: '012',
      oasysFileName: 'offender oasys.pdf',
      createdByUserName: 'Bobby Badger',
      createdDateTime: '2022-03-05T14:15:43.000Z'
    }
  ],
  lastKnownAddressOption: 'YES',
  lastKnownAddresses: [
    {
      fullAddress: '1, LYNN ROAD, WALTON HIGHWAY, WISBECH, PE14 7DF',
      line1: '1, LYNN ROAD',
      town: 'WISBECH',
      postcode: 'PE14 7DF',
    },
    {
      line1: '345 Porchester Road',
      line2: 'Southsea',
      town: 'Portsmouth',
      postcode: 'PO1 4OY',
    },
  ],
  bookingNumber: '12345C',
  probationOfficerName: 'Dave Angel',
  probationOfficerPhoneNumber: '07473739388',
  probationOfficerEmail: 'probation.office@justice.com',
  localDeliveryUnit: 'CENTRAL_AUDIT_TEAM',
  localDeliveryUnitLabel: 'Central Audit Team',
  authorisingAssistantChiefOfficer: 'Bob Monkfish',
  licenceConditionsBreached: '(i) one\n(ii) two',
  reasonsForRecall: ['OTHER', 'ELM_FAILURE_CHARGE_BATTERY'],
  reasonsForRecallOtherDetail: 'other reason detail...',
  currentPrison: 'BFI',
  currentPrisonLabel: 'Bedford (HMP)',
  rescindRecords: [
    {
      rescindRecordId: '123',
      requestEmailId: '123',
      requestEmailFileName: 'rescind-request.msg',
      requestEmailReceivedDate: '2020-12-08',
      requestDetails: 'Rescind was requested by email',
      approved: true,
      decisionDetails: 'Rescind was confirmed by email',
      decisionEmailId: '456',
      decisionEmailFileName: 'rescind-confirm.msg',
      decisionEmailSentDate: '2020-12-09',
      version: 1,
      createdByUserName: 'Bobby Badger',
      createdDateTime: '2020-12-09T12:24:03.000Z',
      lastUpdatedDateTime: '2020-12-09T12:24:03.000Z',
    },
  ],
  additionalLicenceConditions: true,
  additionalLicenceConditionsDetail: 'one, two',
  differentNomsNumber: true,
  differentNomsNumberDetail: 'A1234AA',
  recallNotificationEmailSentDateTime: twoDaysAgo.toISO(),
  dossierEmailSentDate: yesterday.toISODate(),
  hasDossierBeenChecked: true,
  recallAssessmentDueDateTime: tomorrow.toISO(),
  dossierTargetDate: tomorrow.toISODate(),
  warrantReferenceNumber: '04RC/6457367A74325',
  returnedToCustodyDateTime: '2022-01-22T13:45:33.000Z',
  returnedToCustodyNotificationDateTime: '2022-01-23T08:22:06.000Z',
  stopReason: 'DECEASED',
  rereleaseSupported: false,
  legalRepresentativeInfo: {
    fullName: 'Jenny Eclair',
    phoneNumber: '07824637629',
    email: 'jenny@legalreps.com'
  },
  seniorProbationOfficerInfo: {
    fullName: 'Mark Harvard',
    phoneNumber: '07383789332',
    email: 'mark@probation.com',
    functionalEmail: 'general@probation.com'
  }
};

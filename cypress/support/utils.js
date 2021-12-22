import { DateTime, Settings } from 'luxon'

Settings.throwOnInvalid = true

export const europeLondon = 'Europe/London'
const datePresentationFormat = 'd MMMM yyyy'
const datePresentationNoYearFormat = 'd MMM'
const timePresentationFormat = 'HH:mm'

export const isDefined = (val) => typeof val !== 'undefined'

export const splitIsoDateToParts = (isoDate) => {
  if (!isDefined(isoDate)) {
    return undefined
  }
  try {
    const includeTime = isoDate.length > 10
    const dateTime = getDateTimeInEuropeLondon(isoDate)
    const { year, month, day, hour, minute } = dateTime.toObject()
    const paddedDate = { year: year.toString(), month: padWithZeroes(month), day: padWithZeroes(day) }
    if (includeTime) {
      return { ...paddedDate, hour: padWithZeroes(hour), minute: padWithZeroes(minute) }
    }
    return paddedDate
  } catch (err) {
    return undefined
  }
}

export const formatIsoDate = (isoDate, { dateOnly } = {}) => {
  const dateAndTimePresentationFormat = "d MMMM yyyy' at 'HH:mm"
  if (!isDefined(isoDate)) {
    return undefined
  }
  try {
    const includeTime = isoDate.length > 10 && !dateOnly
    const dateTime = getDateTimeInEuropeLondon(isoDate)

    if (includeTime) {
      return dateTime.toFormat(dateAndTimePresentationFormat)
    }
    return dateTime.toFormat(datePresentationFormat)
  } catch (err) {
    return ''
  }
}

export function getDateTimeUTC(isoDate) {
  return DateTime.fromISO(isoDate, { zone: 'utc' })
}

export function isoDateToMillis(isoDate) {
  return getDateTimeUTC(isoDate).toMillis()
}

export function getDateTimeInEuropeLondon(isoDate) {
  return getDateTimeUTC(isoDate).setZone(europeLondon)
}

export const padWithZeroes = (value) => {
  if (!isDefined(value)) {
    return ''
  }
  const padded = value.toString()
  return padded.length < 2 ? `0${padded}` : padded
}

export const booleanToYesNo = bool => (bool ? 'Yes' : 'No')
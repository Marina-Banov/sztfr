/**
 * Combines date and time into one variable
 * @param date {string}
 * @param time {string}
 * @returns {string} result in ISO format
 */
export function combineDateTime(date, time) {
  if (!date || !time) {
    return "";
  }
  const d = new Date(date);
  const t = new Date(time);
  const res = new Date(
    d.getFullYear(),
    d.getMonth(),
    d.getDate(),
    t.getHours(),
    t.getMinutes(),
    0
  );
  return res.toISOString();
}

/**
 * Checks if start date is before end date
 * @param start {string}
 * @param end {string}
 * @returns {boolean}
 */
export function validDateRange(start, end) {
  if (!start || !end) {
    return true;
  }
  return new Date(end).getTime() >= new Date(start).getTime();
}

/**
 * Converts format HH:mm to ISO date
 * @param t {string}
 * @returns {string}
 */
export function getISOTime(t) {
  if (!t) {
    return t;
  }
  const d = new Date();
  const h = t.split(":");
  d.setHours(parseInt(h[0], 10), parseInt(h[1], 10));
  return d.toISOString();
}

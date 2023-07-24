
- [ ] The entire CSV is in the UTF-8 character set.
- [ ] The Timestamp column should be formatted in RFC3339 format.
- [ ] The Timestamp column should be assumed to be in US/Pacific time; please convert it to US/Eastern.
- [ ] All ZIP codes should be formatted as 5 digits. If there are less than 5 digits, assume 0 as the prefix.
- [ ] The FullName column should be converted to uppercase. There will be non-English names.
- [ ] The Address column should be passed through as is, except for Unicode validation. Please note there are commas in the Address field; your CSV parsing will need to take that into account. Commas will only be present inside a quoted string.
- [ ] The FooDuration and BarDuration columns are in HH:MM:SS.MS format (where MS is milliseconds); please convert them to the total number of seconds.
- [ ] The TotalDuration column is filled with garbage data. For each row, please replace the value of TotalDuration with the sum of FooDuration and BarDuration.
- [ ] The Notes column is free form text input by end-users; please do not perform any transformations on this column. If there are invalid UTF-8 characters, please replace them with the Unicode Replacement Character.

Safe Assumptions:

- [ ] The input document is in UTF-8, although some characters may be incorrectly encoded.
- [ ] Invalid characters can be replaced with the Unicode Replacement Character. If that replacement makes data invalid (for example, because it turns a date field into something unparseable), print a warning to stderr and drop the row from your output.
- [ ] Times that are missing timezone information are in US/Pacific.
- [ ] The sample data we provide contains all date and time format variants you will need to handle.
- [ ] Any type of line endings are permissible in the output.


====

Questions: round up to seconds from format?
SELECT
CONCAT(first, ' ', IFNULL(CONCAT(middle, ' '), ''), last) AS 'name',
CONCAT(usfid, '@usfca.edu') AS 'email',
IFNULL(twitterid, '') AS 'twitter',
GROUP_CONCAT(course ORDER BY course SEPARATOR ', ') AS 'courses'
FROM faculty_names
NATURAL LEFT OUTER JOIN faculty_twitter
NATURAL LEFT OUTER JOIN faculty_courses
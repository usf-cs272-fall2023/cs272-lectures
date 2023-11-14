SELECT
CONCAT(first, ' ', IFNULL(CONCAT(middle, '. '), ''), last) AS 'name',
CONCAT(usfid, '@usfca.edu') AS 'email',
IFNULL(githubid, '') AS 'github',
GROUP_CONCAT(course ORDER BY course SEPARATOR ', ') AS 'courses'
FROM faculty_names
NATURAL LEFT OUTER JOIN faculty_github
NATURAL LEFT OUTER JOIN faculty_courses
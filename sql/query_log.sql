SET GLOBAL general_log = 'ON';
SET GLOBAL general_log_file = '/var/log/mysql/query.log';

SHOW VARIABLES LIKE '%log_output%';
SHOW VARIABLES LIKE '%general_log%';
SHOW VARIABLES LIKE '%slow_query%';

SELECT *
from mysql.general_log
ORDER BY event_time DESC;
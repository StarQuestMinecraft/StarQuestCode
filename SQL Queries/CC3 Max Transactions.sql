select id, name, uuid, Total from 
	minecraft.cc3_account as acc 
JOIN 
	(SELECT username_id, SUM(amount) as Total FROM minecraft.cc3_log where amount > 50000 and ISNULL(causeReason) GROUP BY username_id) as total
 on acc.id=total.username_id ORDER BY Total desc;
SELECT * FROM minecraft.bungeeplayers as ply INNER JOIN
	(SELECT ipaddress, count(*) as count FROM minecraft.bungeeplayers
		GROUP BY ipaddress HAVING count > 1
	) AS dupes
ON ply.ipaddress=dupes.ipaddress
ORDER BY ply.ipaddress;
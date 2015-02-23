SELECT CONCAT("SET SQL_SAFE_UPDATES=0;UPDATE towny.", table_name, " SET friends='' WHERE friends != '';") AS com FROM information_schema.tables WHERE table_name LIKE "%_residents";

SET SQL_SAFE_UPDATES=0;UPDATE towny.acualis_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.asteroidbelt_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.boletarian_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.boskevine_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.ceharram_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.defalos_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.digitalia_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.drakos_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.emera_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.iffrizar_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.inaris_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.kelakaria_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.krystallos_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.quavara_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.regalis_residents SET friends='' WHERE friends != '';
SET SQL_SAFE_UPDATES=0;UPDATE towny.valadro_residents SET friends='' WHERE friends != '';

(SELECT name,town,friends FROM towny.acualis_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.asteroidbelt_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.boletarian_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.boskevine_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.ceharram_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.defalos_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.digitalia_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.drakos_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.emera_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.iffrizar_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.inaris_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.kelakaria_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.krystallos_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.quavara_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.regalis_residents WHERE friends != "")
UNION (SELECT name,town,friends FROM towny.valadro_residents WHERE friends != "");
    

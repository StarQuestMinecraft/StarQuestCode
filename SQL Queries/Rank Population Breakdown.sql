SELECT parent, COUNT(*) as count FROM minecraft.permissions_inheritance where type=1 GROUP BY parent;
UPDATE minecraft.permissions_inheritance SET parent='Settler' WHERE parent='AMBIG';
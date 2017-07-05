SELECT shdr.description
FROM t_showroom_hdr shdr
ORDER BY upper(TRIM(shdr.market)) ASC,
	shdr.market DESC, --add original col desc to force lower case first
	upper(TRIM(shdr.description)) DESC,
	shdr.description DESC; --add original col desc to force lower case first

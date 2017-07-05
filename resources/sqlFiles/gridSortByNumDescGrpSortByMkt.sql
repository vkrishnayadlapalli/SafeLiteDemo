SELECT TRIM(to_char(round(shdr.price, 4), '999999999990.0000')) price
  FROM t_showroom_hdr shdr
 ORDER BY upper(shdr.market) ASC, shdr.price DESC;
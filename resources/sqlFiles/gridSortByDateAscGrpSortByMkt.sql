SELECT shdr.transaction_date
  FROM t_showroom_hdr shdr
 ORDER BY upper(shdr.market) ASC, shdr.transaction_date ASC;

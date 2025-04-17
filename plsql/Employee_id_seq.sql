CREATE SEQUENCE employe_id_seq
  --generate automatic ids for the employees--
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE --so that it doesn't restart from 1 if the maximum value is reached--;

CREATE SEQUENCE reservation_id_seq
  --generating automatic reservation ids for the reservations--
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE --so that it doesn't return to 1 if the maximum value is reached--;

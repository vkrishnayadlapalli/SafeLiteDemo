DECLARE
  s_location_code_1  t_location.location_code%TYPE := 'TWX002';
  d_week_ending_date t_labor_schedule_week.week_ending_date%TYPE := '09-OCT-2016';
BEGIN

  DELETE FROM CEP_OPEN_SHIFT_ALT_POS_XREF
   WHERE open_shift_id IN
         (SELECT cos.id
            FROM cep_open_shift cos
           WHERE cos.start_time >= d_week_ending_date
             AND cos.location_id IN
                 (SELECT loc.location_id
                    FROM t_location loc
                   WHERE loc.location_code IN (s_location_code_1)));

  DELETE FROM CEP_ACTIVITY_LOG
   WHERE shift_request_id IN
         (SELECT id
            FROM CEP_OPEN_SHIFT_ACCOUNT_XREF
           WHERE open_shift_id IN
                 (SELECT cos.id
                    FROM cep_open_shift cos
                   WHERE cos.start_time >= d_week_ending_date
                     AND cos.location_id IN
                         (SELECT loc.location_id
                            FROM t_location loc
                           WHERE loc.location_code IN (s_location_code_1))));

  DELETE FROM CEP_OPEN_SHIFT_ACCOUNT_XREF
   WHERE open_shift_id IN
         (SELECT cos.id
            FROM cep_open_shift cos
           WHERE cos.start_time >= d_week_ending_date
             AND cos.location_id IN
                 (SELECT loc.location_id
                    FROM t_location loc
                   WHERE loc.location_code IN (s_location_code_1)));

  DELETE FROM CEP_OPEN_SHIFT_SKILL_XREF
   WHERE open_shift_id IN
         (SELECT cos.id
            FROM cep_open_shift cos
           WHERE cos.start_time >= d_week_ending_date
             AND cos.location_id IN
                 (SELECT loc.location_id
                    FROM t_location loc
                   WHERE loc.location_code IN (s_location_code_1)));

  DELETE FROM CEP_SHIFT_SCHEDULE_TIMESLOT
   WHERE schedule_data_id IN
         (SELECT id
            FROM CEP_SHIFT_SCHEDULE_DATA
           WHERE open_shift_id IN
                 (SELECT cos.id
                    FROM cep_open_shift cos
                   WHERE cos.start_time >= d_week_ending_date
                     AND cos.location_id IN
                         (SELECT loc.location_id
                            FROM t_location loc
                           WHERE loc.location_code IN (s_location_code_1))));

  DELETE FROM CEP_SHIFT_SCHEDULE_SKILL
   WHERE schedule_data_id IN
         (SELECT id
            FROM CEP_SHIFT_SCHEDULE_DATA
           WHERE open_shift_id IN
                 (SELECT cos.id
                    FROM cep_open_shift cos
                   WHERE cos.start_time >= d_week_ending_date
                     AND cos.location_id IN
                         (SELECT loc.location_id
                            FROM t_location loc
                           WHERE loc.location_code IN (s_location_code_1))));

  DELETE FROM CEP_SHIFT_SCHEDULE_DATA
   WHERE open_shift_id IN
         (SELECT cos.id
            FROM cep_open_shift cos
           WHERE cos.start_time >= d_week_ending_date
             AND cos.location_id IN
                 (SELECT loc.location_id
                    FROM t_location loc
                   WHERE loc.location_code IN (s_location_code_1)));

  DELETE FROM cep_open_shift cos
   WHERE cos.start_time >= d_week_ending_date
     AND cos.location_id IN
         (SELECT loc.location_id
            FROM t_location loc
           WHERE loc.location_code IN (s_location_code_1));

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;

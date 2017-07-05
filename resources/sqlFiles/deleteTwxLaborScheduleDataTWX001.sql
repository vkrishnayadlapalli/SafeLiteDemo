DECLARE
  s_location_code_1  t_location.location_code%TYPE := 'TWX001';
  d_week_ending_date t_labor_schedule_week.week_ending_date%TYPE := '09-OCT-2016'; 
BEGIN
  DELETE FROM t_labor_schedule_stafftempl lsst
   WHERE lsst.week_ending_date >= d_week_ending_date
     AND lsst.location_id IN
         (SELECT loc.location_id
            FROM t_location loc
           WHERE loc.location_code IN (s_location_code_1));

  DELETE FROM t_labor_schedule_shift_15min labss15m
   WHERE labss15m.labor_schedule_shift_pk IN
         (SELECT lss.labor_schedule_shift_pk
            FROM t_labor_schedule_shift lss, t_location loc
           WHERE lss.location_id = loc.location_id
             AND lss.week_ending_date >= d_week_ending_date
             AND loc.location_code IN (s_location_code_1));

  DELETE FROM t_labor_schedule_shift lss
   WHERE lss.week_ending_date >= d_week_ending_date
     AND lss.location_id IN
         (SELECT loc.location_id
            FROM t_location loc
           WHERE loc.location_code IN (s_location_code_1));

  DELETE FROM t_labor_schedule_day_ideal_ts lsdits
   WHERE lsdits.week_ending_date >= d_week_ending_date
     AND lsdits.location_id IN
         (SELECT loc.location_id
            FROM t_location loc
           WHERE loc.location_code IN (s_location_code_1));

  DELETE FROM t_labor_schedule_day_ideal lsdi
   WHERE lsdi.week_ending_date >= d_week_ending_date
     AND lsdi.location_id IN
         (SELECT loc.location_id
            FROM t_location loc
           WHERE loc.location_code IN (s_location_code_1));

  DELETE FROM t_labor_schedule_day lsd
   WHERE lsd.week_ending_date >= d_week_ending_date
     AND lsd.location_id IN
         (SELECT loc.location_id
            FROM t_location loc
           WHERE loc.location_code IN (s_location_code_1));

  DELETE FROM t_labor_schedule_week lsw
   WHERE lsw.week_ending_date >= d_week_ending_date
     AND lsw.location_id IN
         (SELECT loc.location_id
            FROM t_location loc
           WHERE loc.location_code IN (s_location_code_1));

  DELETE FROM cep_shift_schedule_timeslot
   WHERE schedule_data_id IN
         (SELECT id
            FROM cep_shift_schedule_data
           WHERE schedule_day_id IN
                 (SELECT id
                    FROM cep_shift_schedule_day
                   WHERE labor_schedule_id IN
                         (SELECT cls.id
                            FROM cep_labor_schedule cls
                           WHERE cls.week_ending_date >= d_week_ending_date
                             AND cls.location_id IN
                                 (SELECT loc.location_id
                                    FROM t_location loc
                                   WHERE loc.location_code IN
                                         (s_location_code_1)))));

  DELETE FROM cep_shift_schedule_skill csss
   WHERE csss.schedule_data_id IN
         (SELECT id
            FROM cep_shift_schedule_data
           WHERE schedule_day_id IN
                 (SELECT id
                    FROM cep_shift_schedule_day
                   WHERE labor_schedule_id IN
                         (SELECT cls.id
                            FROM cep_labor_schedule cls
                           WHERE cls.week_ending_date >= d_week_ending_date
                             AND cls.location_id IN
                                 (SELECT loc.location_id
                                    FROM t_location loc
                                   WHERE loc.location_code IN
                                         (s_location_code_1)))));

  DELETE FROM cep_shift_schedule_data
   WHERE schedule_day_id IN
         (SELECT id
            FROM cep_shift_schedule_day
           WHERE labor_schedule_id IN
                 (SELECT cls.id
                    FROM cep_labor_schedule cls
                   WHERE cls.week_ending_date >= d_week_ending_date
                     AND cls.location_id IN
                         (SELECT loc.location_id
                            FROM t_location loc
                           WHERE loc.location_code IN (s_location_code_1))));

  DELETE FROM cep_labor_schedule_option_xref clsfx
   WHERE clsfx.labor_schedule_id IN
         (SELECT cls.id
            FROM cep_labor_schedule cls
           WHERE cls.week_ending_date >= d_week_ending_date
             AND cls.location_id IN
                 (SELECT loc.location_id
                    FROM t_location loc
                   WHERE loc.location_code IN (s_location_code_1)));

  DELETE FROM CEP_DRAFT_SCHEDULE_STAFFTEMPL cdss
   WHERE cdss.labor_schedule_id IN
         (SELECT cls.id
            FROM cep_labor_schedule cls
           WHERE cls.week_ending_date >= d_week_ending_date
             AND cls.location_id IN
                 (SELECT loc.location_id
                    FROM t_location loc
                   WHERE loc.location_code IN (s_location_code_1)));

  DELETE FROM CEP_LABOR_SCHED_STAFF_STRUCT clsss
   WHERE clsss.labor_schedule_id IN
         (SELECT cls.id
            FROM cep_labor_schedule cls
           WHERE cls.week_ending_date >= d_week_ending_date
             AND cls.location_id IN
                 (SELECT loc.location_id
                    FROM t_location loc
                   WHERE loc.location_code IN (s_location_code_1)));

  DELETE FROM cep_shift_schedule_day cssd
   WHERE cssd.labor_schedule_id IN
         (SELECT cls.id
            FROM cep_labor_schedule cls
           WHERE cls.week_ending_date >= d_week_ending_date
             AND cls.location_id IN
                 (SELECT loc.location_id
                    FROM t_location loc
                   WHERE loc.location_code IN (s_location_code_1)));

  DELETE FROM cep_labor_schedule cls
   WHERE cls.week_ending_date >= d_week_ending_date
     AND cls.location_id IN
         (SELECT loc.location_id
            FROM t_location loc
           WHERE loc.location_code IN (s_location_code_1));

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    RAISE;
END;/

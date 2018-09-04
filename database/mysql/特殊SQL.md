## 排名

```sq
SELECT
	obj_new.employee_id,obj_new.rownum,obj_new.score
FROM
	(
		SELECT
			obj.employee_id,
			obj.score ,@rownum := @rownum + 1 AS num_tmp,
			@incrnum := CASE
		WHEN @rowtotal = obj.score THEN
			@incrnum
		WHEN @rowtotal := obj.score THEN
			@rownum
		END AS rownum
		FROM
			(
				SELECT
					employee_id,
					xf_achievement score
				FROM
					rank_day_achievement
				ORDER BY
					xf_achievement DESC
			) AS obj,
			(
				SELECT
					@rownum := 0 ,@rowtotal := NULL ,@incrnum := 0
			) r
	) AS obj_new
WHERE
	obj_new.employee_id = 123;
```


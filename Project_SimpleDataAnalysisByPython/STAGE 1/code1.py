heandcount_Relation = {}
is_first_line = True

for row in open("initial_merge.csv"):
  if is_first_line:
    is_first_line = False
  else:
    values = row.split(",")
    country = values[1]
    headcountRU = float(values[23])
    agriculture = values[18]
    industry = values[19]
    service = values[20]
    if headcountRU > 25.0:
      heandcount_Relation[country] = [agriculture]
      heandcount_Relation[country].append(industry)
      heandcount_Relation[country].append(service)

for key in sorted(heandcount_Relation):
  print(key, ":", heandcount_Relation[key])
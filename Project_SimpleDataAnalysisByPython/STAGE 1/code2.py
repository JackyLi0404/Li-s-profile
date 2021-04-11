heandcount_pop = {}
is_first_line = True

for row in open("initial_merge.csv"):
  if is_first_line:
    is_first_line = False
  else:
    values = row.split(",")
    country = values[1]
    headcountRU = float(values[23])
    population = float(values[3])
    headcount_population = headcountRU * population
    if headcountRU > 25.0:
        heandcount_pop[country] = [headcount_population]
      
for key in sorted(heandcount_pop):
  print(key, ":", heandcount_pop[key])
Region_GDP = {}
Region_population = {}
is_first_line = True

for row in open("initial_merge.csv"):
  if is_first_line:
    is_first_line = False
  else:
    values = row.split(",")
    region = values[2]
    GDP = float(values[9])
    population = float(values[3])
    GDP_pop = GDP * population
    if region not in Region_GDP:
      Region_GDP[region] = [GDP_pop]
      Region_population[region] = [population]
    else:
      Region_GDP[region].append(GDP_pop)
      Region_population[region].append(population)

for key in sorted(Region_GDP):
  GDPK = Region_GDP[key]
  populationK = Region_population[key]
  print(key, ":", round((sum(GDPK)/sum(populationK)),1))
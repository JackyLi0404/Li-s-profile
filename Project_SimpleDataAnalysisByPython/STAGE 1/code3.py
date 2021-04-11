intensityDR_Agriculture = {}
is_first_line = True

for row in open("initial_merge.csv"):
  if is_first_line:
    is_first_line = False
  else:
    values = row.split(",")
    agriculture = values[18]
    agricultureS = agriculture.replace(",",".")
    agricultureF = float(agricultureS)
    intensityDR = float(values[27])
    if intensityDR > 50.0:
      intensity_bin = (intensityDR - 50.0) // 2
      if intensity_bin not in intensityDR_Agriculture:
          intensityDR_Agriculture[intensity_bin] = [agricultureF]
      else:
          intensityDR_Agriculture[intensity_bin].append(agricultureF)

for key in sorted(intensityDR_Agriculture):
  print(key, ":", max(intensityDR_Agriculture[key]))

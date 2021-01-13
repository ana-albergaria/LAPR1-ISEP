# =====================================================POP DIMENSION================================================
# To view in X:
set terminal wxt size 600, 400 enhanced font 'Verdana,10' persist

data = 'populationTotal.dat'

set title "Dimensão da população / tempo"
set xlabel "Tempo"
set ylabel "População"

set style data linespoints

# Plot
plot data title 'População ao longo do tempo'
# =====================================================POP DIMENSION VARIATION================================================
set terminal wxt 1 size 600, 400 enhanced font 'Verdana,10' persist

set title "Taxa de variação da dimensão da população / tempo"
set xlabel "Tempo"
set ylabel "Taxa de variação"

set style data linespoints
lastline=ARG2
# Plot
plot [ :lastline][0:] data using 1:3 title 'variação da população'

# =====================================================POP DISTRIBUTION================================================
set terminal wxt 2 size 600, 400 enhanced font 'Verdana,10' persist

set title "Distribuição da população / tempo"
set xlabel "Tempo"
set ylabel "Distribuição da população"

set style data linespoints
# Plot
plot for [i=1:ARG1] 'class'.i.'.dat' title 'Classe '.i

# =====================================================POP DISTRIBUTION NORMALIZED================================================
set terminal wxt 3 size 600, 400 enhanced font 'Verdana,10' persist

set title "Distribuição da população normalizada / tempo"
set xlabel "Tempo"
set ylabel "Distribuição da população normalizada"

set style data linespoints
# Plot
plot for [i=1:ARG1] 'class'.i.'.dat' using 1:3 title 'Classe '.i
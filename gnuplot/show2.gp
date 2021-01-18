# =====================================================POP DIMENSION VARIATION================================================
set terminal wxt 1 size 600, 400 enhanced font 'Verdana,10'

data = 'gnuplot/populationTotal.dat'

set title "Taxa de variação da dimensão da população / tempo"
set xlabel "Tempo"
set ylabel "Taxa de variação"

set style data linespoints
lastline=ARG2

# Plot
plot [ :lastline][0:] data using 1:3 title 'variação da população'
pause mouse close
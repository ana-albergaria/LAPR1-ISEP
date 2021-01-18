# =====================================================POP DIMENSION================================================
# To view in X:
set terminal wxt size 600, 400 font 'Verdana,10'
data = 'gnuplot/populationTotal.dat'

set title "Dimensão da população / tempo"
set xlabel "Tempo"
set ylabel "População"

set style data linespoints

# Plot
plot data title 'População ao longo do tempo'
pause mouse close

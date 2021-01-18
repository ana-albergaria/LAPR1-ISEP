# =====================================================POP DISTRIBUTION================================================
set terminal wxt 2 size 600, 400 enhanced font 'Verdana,10'

set title "Distribuição da população / tempo"
set xlabel "Tempo"
set ylabel "Distribuição da população"

set style data linespoints
# Plot
plot for [i=1:ARG1] 'gnuplot/class'.i.'.dat' title 'Classe '.i
pause mouse close

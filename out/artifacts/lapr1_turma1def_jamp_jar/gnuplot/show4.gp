# =====================================================POP DISTRIBUTION NORMALIZED================================================
set terminal wxt 3 size 600, 400 enhanced font 'Verdana,10'

set title "Distribuição da população normalizada / tempo"
set xlabel "Tempo"
set ylabel "Distribuição da população normalizada"

set style data linespoints
# Plot
plot for [i=1:ARG1] 'gnuplot/class'.i.'.dat' using 1:3 title 'Classe '.i
pause mouse close
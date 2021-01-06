# To view in X:

# To print on a PostScript printer:
set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 600, 400 
set output 'teste.png'

set title "Teste Plot"
set xlabel "X"
set ylabel "Y"

set style data linespoints

f(x) = 2 * sin(x)
g(x) = 2 * cos(x)

# Plot
plot f(x) title 'sin(x)' with lines linestyle 1, \
     g(x) title 'cos(x)' with lines linestyle 2

# Executar este script gnuplot na linha de comandos
# C:\ gnuplot exemploGnuplot.gp

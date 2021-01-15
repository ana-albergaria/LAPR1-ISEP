# =====================================================POP DISTRIBUTION NORMALIZED================================================
if (ARG1==1){
   # print a png
   set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 600, 400
   set output 'normalizedDistribution-'.ARG4.'.png'
}
if(ARG1==2) {
    # To print on a PostScript printer:
    set terminal postscript
    set output "normalizedDistribution.eps"
}
if(ARG1==3){
    # To print on a Txt printer:
    set terminal dumb
    set output 'normalizedDistribution.txt'
}

set title "Distribuição da população normalizada / tempo"
set xlabel "Tempo"
set ylabel "Distribuição da população normalizada"

set style data linespoints
# Plot
plot for [i=1:ARG2] 'gnuplot/class'.i.'.dat' using 1:3 title 'Classe '.i
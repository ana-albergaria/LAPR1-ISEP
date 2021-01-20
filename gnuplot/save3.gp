# =====================================================POP DISTRIBUTION================================================
if (ARG1==1){
   # print a png
   set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 600, 400
   set output ARG5.'/popDistribution-'.ARG4.'.png'
}
if(ARG1==2) {
    # To print on a PostScript printer:
    set terminal postscript
    set output ARG5.'/popDistribution-'.ARG4.".eps"
}
if(ARG1==3){
    # To print on a Txt printer:
    set terminal dumb
    set output ARG5.'/popDistribution-'.ARG4.'.txt'
}

set title "Distribuição da população / tempo"
set xlabel "Tempo"
set ylabel "Distribuição da população"

set style data linespoints
# Plot
plot for [i=1:ARG2] 'gnuplot/class'.i.'.dat' title 'Classe '.i

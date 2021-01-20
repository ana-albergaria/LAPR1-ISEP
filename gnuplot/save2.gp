# =====================================================POP DIMENSION VARIATION================================================

if (ARG1==1){
   # print a png
   set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 600, 400
   set output ARG5.'/popVariation-'.ARG4.'.png'
}
if(ARG1==2) {
    # To print on a PostScript printer:
    set terminal postscript
    set output ARG5.'/popVariation-'.ARG4.'.eps'
}
if(ARG1==3){
    # To print on a Txt printer:
    set terminal dumb
    set output ARG5.'/popVariation-'.ARG4.'.txt'
}

data = 'gnuplot/populationTotal.dat'

set title "Taxa de variação da dimensão da população / tempo"
set xlabel "Tempo"
set ylabel "Taxa de variação"

set style data linespoints
lastline=ARG3
# Plot
plot [ :lastline][0:] data using 1:3 title 'variação da população'

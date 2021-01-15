# =====================================================POP DIMENSION================================================
# To view in X:
if (ARG1==1){
   # print a png
   set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 600, 400
   set output 'popDimension-'.ARG4.'.png'
}
if(ARG1==2) {
    # To print on a PostScript printer:
    set terminal postscript
    set output "popDimension.eps"
}
if(ARG1==3){
    # To print on a Txt printer:
    set terminal dumb
    set output 'popDimension.txt'
}

data = 'gnuplot/populationTotal.dat'

set title "Dimensão da população / tempo"
set xlabel "Tempo"
set ylabel "População"

set style data linespoints

# Plot
plot data title 'População ao longo do tempo'
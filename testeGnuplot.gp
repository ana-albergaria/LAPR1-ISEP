# =====================================================POP DIMENSION================================================
# To view in X:
if (ARG1==1){
   # print a png
   set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 600, 400
   set output 'popDimension.png'
}
if(ARG1==2) {
    # To print on a PostScript printer:
    set terminal postscript
    set output "popDimension.eps"
}
if(ARG1==3){
    # To print on a Txt printer:
    set terminal postscript
    set output "popDimension.txt"
}

data = 'populationTotal.dat'

set title "Dimensão da população / tempo"
set xlabel "Tempo"
set ylabel "População"

set style data linespoints

# Plot
plot data title 'População ao longo do tempo'
# =====================================================POP DIMENSION VARIATION================================================

if (ARG1==1){
   # print a png
   set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 600, 400
   set output 'popVariation.png'
}
if(ARG1==2) {
    # To print on a PostScript printer:
    set terminal postscript
    set output "popVariation.eps"
}
if(ARG1==3){
    # To print on a Txt printer:
    set terminal postscript
    set output "popVariation.txt"
}

set title "Taxa de variação da dimensão da população / tempo"
set xlabel "Tempo"
set ylabel "Taxa de variação"

set style data linespoints

# Plot
plot data using 1:3 skip 1 title 'variação da população'

# =====================================================POP DISTRIBUTION================================================
if (ARG1==1){
   # print a png
   set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 600, 400
   set output 'popDistribution.png'
}
if(ARG1==2) {
    # To print on a PostScript printer:
    set terminal postscript
    set output "popDistribution.eps"
}
if(ARG1==3){
    # To print on a Txt printer:
    set terminal postscript
    set output "popDistribution.txt"
}

set title "Distribuição da população / tempo"
set xlabel "Tempo"
set ylabel "Distribuição da população"

set style data linespoints
# Plot
plot for [i=1:ARG2] 'class'.i.'.dat' title 'Classe '.i

# =====================================================POP DISTRIBUTION NORMALIZED================================================
if (ARG1==1){
   # print a png
   set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 600, 400
   set output 'normalizedDistribution.png'
}
if(ARG1==2) {
    # To print on a PostScript printer:
    set terminal postscript
    set output "normalizedDistribution.eps"
}
if(ARG1==3){
    # To print on a Txt printer:
    set terminal postscript
    set output "normalizedDistribution.txt"
}

set title "Distribuição da população normalizada / tempo"
set xlabel "Tempo"
set ylabel "Distribuição da população normalizada"

set style data linespoints
# Plot
plot for [i=1:ARG2] 'class'.i.'.dat' using 1:3 title 'Classe '.i
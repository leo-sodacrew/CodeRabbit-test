#!/bin/bash

rm -rf ../resources/static
echo "exeucute 'rm -rf ../resources/static'"
mkdir ../resources/static
echo "exeucute 'mkdir ../resources/static'"
cp -r build/static ../resources/
echo "exeucute 'cp -r build/static ../resources/'"
cp build/* ../resources/static/
echo "exeucute 'cp build/* ../resources/static/'"
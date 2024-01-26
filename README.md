# PDF worker application

## The Genesis
The inspiration for this application came from my work at the Polish local newspaper 'Czas Ostrzeszowski'. 
While working there, I wrote several Python scripts to merge each individual page of the newspaper into a single PDF file corresponding to the issue number (as part of a service to transfer newspaper issues to electronic archives of neighboring cities). I consolidated the logic of these scripts into a single Java FX application.

## Features
- Merge PDF files
- Reduce the size of the merged PDF file

## UI
![main](https://github.com/andreichernetskii/PDF_Worker_App/assets/73879364/c74474c9-8467-42c4-9b99-047b8eb904fc)

## How To Use
1. In JDE's terminal, run the command: ``` mvn compile javafx:run ```.
2. In application window, choose the PDF files you need to merge.
   - If you only need merge files, select "Merge files".
   - If you need to reduse the size of the file after merging, select "Reduce file".
3. Choose the folder to save the merged PDF file.
4. Press "Start".
5. When process is complete, you will see notification "Merging file complete!".

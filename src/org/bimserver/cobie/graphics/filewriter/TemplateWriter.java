package org.bimserver.cobie.graphics.filewriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.shared.Common;
import org.bimserver.cobie.shared.utility.StringUtils;

/**
 * Writes an HTML document for a HTML file template. *
 */
public abstract class TemplateWriter extends FileWriter
{
    private Path rootPath;

	protected TemplateWriter(Path rootPath, GlobalSettings settings)
    {
        super(settings);
		this.rootPath = rootPath;
    }

    private static String parseTemplate(InputStream inputStream) throws IOException
    {
        BufferedReader templateReader = new BufferedReader(new InputStreamReader(inputStream));

        String currentLine;
        String template = Common.EMPTY_STRING.toString();

        do
        {
            currentLine = templateReader.readLine();

            if (!StringUtils.isNullOrEmpty(currentLine))
            {
                template += currentLine;
                template += StringUtils.EOL; // readLine() strips the newline character.
            }

        } while (currentLine != null);

        templateReader.close();
        return template;
    }

    public String getTemplate(Resource templateFile) throws IOException
    {
        Path file = rootPath.resolve(templateFile.getRelativePath().toString());
        InputStream inputStream = Files.newInputStream(file);
        try {
        	if (inputStream == null) {
        		System.out.println(templateFile.getRelativePath().toString() + " not found");
        		return null;
        	} else {
        		return parseTemplate(inputStream);
        	}
        } finally {
        	inputStream.close();
        }
    }
}

package org.erdc.cobie.graphics.filewriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.erdc.cobie.graphics.settings.GlobalSettings;
import org.erdc.cobie.shared.Common;
import org.erdc.cobie.shared.utility.StringUtils;

/**
 * Writes an HTML document for a HTML file template. *
 */
public abstract class TemplateWriter extends FileWriter
{
    protected TemplateWriter(GlobalSettings settings)
    {
        super(settings);
    }

    private static String parseTemplate(File templateFile) throws IOException
    {
        BufferedReader templateReader = new BufferedReader(new FileReader(templateFile));

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
        return parseTemplate(
                getSettings().
                getOutputSettings().
                getResource(templateFile.getRelativePath()));
    }
}

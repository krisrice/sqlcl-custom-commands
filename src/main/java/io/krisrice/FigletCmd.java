package io.krisrice;

import java.io.IOException;
import java.sql.Connection;

import com.github.lalyos.jfiglet.FigletFont;

import oracle.dbtools.extension.SQLCLService;
import oracle.dbtools.raptor.newscriptrunner.CommandListener;
import oracle.dbtools.raptor.newscriptrunner.IHelp;
import oracle.dbtools.raptor.newscriptrunner.ISQLCommand;
import oracle.dbtools.raptor.newscriptrunner.ScriptRunnerContext;

public class FigletCmd extends CommandListener implements IHelp, SQLCLService {
	@Override
	public boolean handleEvent(Connection conn, ScriptRunnerContext ctx, ISQLCommand cmd) {
		if (matches("banner", cmd.getSql())) {
			String sql = cmd.getSql().replace("\n", "").replace("banner ", "");
		    String asciiArt1;
			try {
				asciiArt1 = FigletFont.convertOneLine(sql==null?"":sql);
			    ctx.write(asciiArt1);
			} catch (IOException e) {
				ctx.write(e.getMessage());
			}
			return true;
		}
		return false;
	}
	@Override
	public void beginEvent(Connection conn, ScriptRunnerContext ctx, ISQLCommand cmd) {}
	@Override
	public void endEvent(Connection conn, ScriptRunnerContext ctx, ISQLCommand cmd) {}

	@Override
	public Class<? extends CommandListener> getCommandListener() {
		return this.getClass();
	}

	@Override
	public String getCommand() {return "banner";}

	@Override
	public String getHelp() {return "something helpful";}

	@Override
	public boolean isSqlPlus() {return false;}

}

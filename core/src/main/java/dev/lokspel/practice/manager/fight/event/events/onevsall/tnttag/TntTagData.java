package dev.lokspel.practice.manager.fight.event.events.onevsall.tnttag;

import dev.lokspel.practice.manager.fight.event.enums.EventType;
import dev.lokspel.practice.manager.fight.event.interfaces.EventData;
import dev.lokspel.practice.manager.fight.event.interfaces.EventFileData;
import java.io.IOException;

public class TntTagData extends EventData {

  public TntTagData() {
    super(EventType.TNTTAG);
  }

  @Override
  protected void setCustomData(EventFileData data) {}

  @Override
  protected void getCustomData(EventFileData data) {}

  @Override
  protected void enable() throws IOException {}
}

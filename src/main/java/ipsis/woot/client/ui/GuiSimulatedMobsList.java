package ipsis.woot.client.ui;

import ipsis.woot.network.DropRegistryStatusReply;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.entity.EntityType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;


/**
 * Background is sized width x height
 * List is sized x0,y0 -> x1,y1
 *
 * Constructor sets
 * x0 to 0,
 * x1 to width
 * y0 to y0
 * y1 to y1
 */
public class GuiSimulatedMobsList extends ExtendedList {

   private OracleScreen oracleScreen;
   private final int listWidth;

   public GuiSimulatedMobsList(OracleScreen oracleScreen, int listWidth) {
       super(oracleScreen.getMinecraftInstance(),
               listWidth, // background width
               oracleScreen.height,  // background height
               16,  // y0
               oracleScreen.height - 16,  // y1
               oracleScreen.getFontRenderer().FONT_HEIGHT + 8); // itemHeight
       this.oracleScreen = oracleScreen;
       this.listWidth = listWidth;
       this.refreshList();
       setRenderSelection(true);
   }

    void refreshList() {
       this.clearEntries();
       oracleScreen.buildSimulatedMobsList(this::addEntry, mob -> new SimulatedMobEntry(mob, this.oracleScreen));
   }

    @Override
    protected int getScrollbarPosition() {
       return this.listWidth;
    }

    @Override
    public int getRowWidth() {
       return this.listWidth;
    }

    @Override
    protected void renderBackground() {
       this.oracleScreen.renderBackground();
    }

    class SimulatedMobEntry extends ExtendedList.AbstractListEntry<SimulatedMobEntry> {
      private final OracleScreen oracleScreen;
      private DropRegistryStatusReply.SimMob simulatedMob;

      SimulatedMobEntry(DropRegistryStatusReply.SimMob simulatedMob, OracleScreen oracleScreen) {
          this.simulatedMob = simulatedMob;
          this.oracleScreen = oracleScreen;
      }

      @Override
      public void render(int entryIdx, int top, int left, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
          String name = "Unknown Mob";
          EntityType<?> entityType = ForgeRegistries.ENTITIES.getValue(simulatedMob.fakeMob.getResourceLocation());
          if (entityType != null) {
              name = new TranslationTextComponent(entityType.getTranslationKey()).getFormattedText();
              if (simulatedMob.fakeMob.hasTag())
                  name = name + " [" + simulatedMob.fakeMob.getTag() + "]";
          }

          FontRenderer font = this.oracleScreen.getFontRenderer();
          font.drawString(font.trimStringToWidth(name, listWidth),
                  left + 3,
                  top + 2,
                  0xFFFFFFFF);

          if (!simulatedMob.simulationStatus[0] || !simulatedMob.simulationStatus[1] || !simulatedMob.simulationStatus[2] || !simulatedMob.simulationStatus[3]) {
              font.drawString(font.trimStringToWidth("L", listWidth),
                      listWidth - 10,
                      top + 2,
                      0xFFFFFFFF);
          }
      }

       @Override
       public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
         oracleScreen.setSelected(this);
         GuiSimulatedMobsList.this.setSelected(this);
         return false;
       }

        public DropRegistryStatusReply.SimMob getInfo() {
          return simulatedMob;
       }
   }
}

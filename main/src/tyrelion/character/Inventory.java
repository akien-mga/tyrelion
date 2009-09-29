/**
 * 
 */
package tyrelion.character;

import java.awt.Point;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import tyrelion.itemsystem.Food;
import tyrelion.itemsystem.Item;

/**
 * @author Basti
 *
 */
public class Inventory {
	
	class InventoryField{
		
		/** Item stored in this inventory field */
		private FieldContent content;
		
		public InventoryField(FieldContent content){
			this.content = content;
			
		}
		
		/** puts an item into this field */
		public FieldContent put(Item item){
			FieldContent old_field = this.content;
			
			if (item.getUid() == this.content.getItem().getUid()) {
				if (content.getItem().isStackable()){ 
					this.content = new InventoryStack(content.getItem(), content.getCount()+1);
					return null;
				} else {this.content = new InventoryItem(item); return old_field;}
			} else {
				this.content = new InventoryItem(item); return old_field;
			}
		}
		
		/** puts intern content into this field */
		public FieldContent put(FieldContent item){
			FieldContent old_field = this.content;
			
			if (item.getItem().getUid() == this.content.getItem().getUid()) {
				if (content.getItem().isStackable()){ 
					this.content = new InventoryStack(content.getItem(), content.getCount()+1);
					return null;
				} else {this.content = item; return old_field;}
			} else {
				this.content = item; return old_field;
			}
		}
		
		public boolean isFull(){
			return !(content.getCount()<5);
		}
		
		public int getCount(){
			return content.getCount();
		}
		
		/** returns the item stored in this field */
		public Item getItem(){
			return content.getItem();
		}
		
	}
	
	interface FieldContent{
		
		Item item = null;
		
		public Item getItem();
		
		public int getCount();
		
		
	}
	
	private class InventoryItem implements FieldContent{
		
		private Item item;
		
		public InventoryItem(Item item){
			this.item = item;
		}
		
		public Item getItem(){
			return item;
		}
		
		public int getCount(){
			return 1;
		}
		
	}
	
	private class InventoryStack implements FieldContent{
		
		private Item item;
		private int count;
		
		public InventoryStack(Item item, int count){
			this.item = item;
			this.count = count;
		}
		
		public Item getItem(){
			return item;
		}
		
		public int getCount(){
			return count;
		}
		
	}
	
	private int invWidth = 4;
	private int invHeight = 6;
	
	private InventoryField[][] fields;
	
	public Inventory() throws SlickException{
		fields = new InventoryField[invWidth][invHeight];
		

		
		Food apple = new Food(1233, "Krasser Apfel", new Image("res/img/items/apple_world.png"),
				new Image("res/img/items/apple_inv.png"), true);
	}
	
	public boolean addItem(Item item){
		
		Point freeField; 
		
		if (item.isStackable()) {freeField = getPossibleField(item);} else freeField = getFreeField();
		
		
		if (freeField == null) {
			return false;
		} else {
			if (fields[freeField.x][freeField.y] != null){
				fields[freeField.x][freeField.y].put(item);
			} else {
				fields[freeField.x][freeField.y] = new InventoryField(new InventoryItem(item));
			}
			return true;
		}
		
	}
	
	private Point getPossibleField(Item item){
		for (int j = 0; j < invHeight; j++){
			for (int i = 0; i < invWidth; i++){
				if (fields[i][j]!=null){
					if (fields[i][j].getItem().getUid() == item.getUid()) 
						if (!fields[i][j].isFull()) return new Point(i, j);
				}
			}
		}
		
		return getFreeField();
	}
	
	private Point getFreeField(){
		Point field = null;
		
		for (int j = 0; j < invHeight; j++){
			for (int i = 0; i < invWidth; i++){
				if (fields[i][j] == null) return new Point(i, j);
			}
		}
		
		return field;
	}
	
	public void render(Graphics g, int x, int y){
		
		
		
		for (int j = 0; j < invHeight; j++){
			for (int i = 0; i < invWidth; i++){
				InventoryField field = fields[i][j];
				if (field != null){
					g.drawImage(field.getItem().getImage_inv(), x+i*57, y+j*55);
					if (field.getCount()>1) g.drawString(Integer.toString(field.getCount()), x+i*57+30, y+j*55+2);
				}
			}
		}
	}
	
	
	
	
	
}
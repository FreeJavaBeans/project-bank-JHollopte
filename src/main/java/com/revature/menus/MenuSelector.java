package com.revature.menus;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;

public class MenuSelector implements Displayable{
	
	private MenuNode currentMenu;
	
	private User currentUser;
	
	private static MenuSelector ms = new MenuSelector();
	
	private MenuSelector() {
		super();
		this.currentMenu = buildMenus();
	}
	
	@Override
	public String display() {
		// TODO Auto-generated method stub
		return new FancyBorder(currentMenu.getValue()).enhancedDisplay().display();
	}
	
	public void traverse(int i) {
		if(i == -1) {
			this.currentMenu = currentMenu.backwards();
		}else {
			this.currentMenu = currentMenu.forwards(i);
		}
	}
	

	private MenuNode buildMenus() {
		MenuNode login = new MenuNode(new LoginMenu(), null, null);
		return login;
	}
	
	
	public void reset() {
		this.currentUser = null;
		this.currentMenu = buildMenus();
	}
	
	public void handleInput() {
		this.currentMenu.getValue().handleInput();
	}


	public static MenuSelector getMenuSelector() {
		return ms;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public void updateNodeAfterAccount(Account a) {
		MenuNode customerAccountMenu = this.currentMenu;
		MenuNode customerModfiyAccountMenu = new MenuNode(	new ModifiyAccountMenu(a),
															customerAccountMenu,
															null);
		List<MenuNode> camChildren = new ArrayList<MenuNode>();
		camChildren.add(customerModfiyAccountMenu);
		customerAccountMenu.setChildren(camChildren);
	}
	
	public void updateNodeAfterCustomerId() {
		MenuNode employeeMainMenu = this.currentMenu;
		MenuNode customerModfiyAccountMenu = new MenuNode(	new ViewUserAccountsMenu(),
															employeeMainMenu,
															null);
		MenuNode ependacc = new MenuNode(new PendingAccountsMenu(),employeeMainMenu,null);
		MenuNode etransaction = new MenuNode(new TransactionLogMenu(),employeeMainMenu,null);
		List<MenuNode> emmChildren = new ArrayList<MenuNode>();
		emmChildren.add(ependacc);
		emmChildren.add(etransaction);
		emmChildren.add(customerModfiyAccountMenu);
		employeeMainMenu.setChildren(emmChildren);
	}
	
	public void updateNode() {
		Menu current = this.currentMenu.getValue();
		MenuNode parent = this.currentMenu.getParent();
		List<MenuNode> children = this.currentMenu.getChildren();
		MenuNode updatedMenu = new MenuNode(null,parent,children);
		if(current instanceof ViewPendingMTMenu) {
			updatedMenu.setValue(new ViewPendingMTMenu());
		} else if(current instanceof AcceptMTMenu) {
			updatedMenu.setValue(new AcceptMTMenu());
		} else {
			updatedMenu.setValue(current);
		}
		for(MenuNode mn:parent.getChildren()) {
			if(mn.equals(this.currentMenu)) {
				mn = updatedMenu;
				this.currentMenu = updatedMenu;
				break;
			}
		}
	}
	
	public void updateAllChildren(boolean updateParent) {
		MenuNode parent = this.currentMenu.getParent();
		List<MenuNode> children = this.currentMenu.getChildren();
		if(updateParent) {
			for(MenuNode mn:parent.getChildren()) {
				if(mn.getValue() instanceof ViewPendingMTMenu) {
					mn.setValue(new ViewPendingMTMenu());
				} else if(mn.getValue() instanceof AcceptMTMenu) {
					mn.setValue(new AcceptMTMenu());
				}
			}
		}else {
			for(MenuNode mn : children) {
				if(mn.getValue() instanceof AccountMenu) {
					mn.setValue(new AccountMenu());
				} else if(mn.getValue() instanceof MoneyTransferMenu) {
					for(MenuNode childrenMNs : mn.getChildren()) {
						if(childrenMNs.getValue() instanceof CreateMTMenu) {
							childrenMNs.setValue(new CreateMTMenu());
						}
					}

				}
			}
		}
	}

	public void updateNodesAfterLogin() {
		MenuNode login = this.currentMenu;
		if(this.currentUser instanceof Customer) {
			MenuNode main = new MenuNode(new MainMenu(), login, null);
			MenuNode customerAccount = new MenuNode(new AccountMenu(), main, null);
			MenuNode mtmenunode = new MenuNode(new MoneyTransferMenu(), main, null);
			MenuNode createmt = new MenuNode(new CreateMTMenu(),mtmenunode,null);
			MenuNode viewmt = new MenuNode(new ViewPendingMTMenu(),mtmenunode,null);
			MenuNode acceptmt = new MenuNode(new AcceptMTMenu(),mtmenunode,null);
			
			List<MenuNode> loginChildren = new ArrayList<MenuNode>();
			loginChildren.add(main);
			login.setChildren(loginChildren);
			
			List<MenuNode> mainChildren = new ArrayList<MenuNode>();
			mainChildren.add(customerAccount);
			mainChildren.add(mtmenunode);
			main.setChildren(mainChildren);
			
			List<MenuNode> mtmenuChildren = new ArrayList<MenuNode>();
			mtmenuChildren.add(createmt);
			mtmenuChildren.add(viewmt);
			mtmenuChildren.add(acceptmt);
			mtmenunode.setChildren(mtmenuChildren);
			
		} else if(this.currentUser instanceof Employee) {
			MenuNode emain = new MenuNode(new EMainMenu(),login,null);
			MenuNode ependacc = new MenuNode(new PendingAccountsMenu(),emain,null);
			MenuNode etransaction = new MenuNode(new TransactionLogMenu(),emain,null);
			
			List<MenuNode> loginChildren = new ArrayList<MenuNode>();
			loginChildren.add(emain);
			login.setChildren(loginChildren);
			
			List<MenuNode> emainChildren = new ArrayList<MenuNode>();
			emainChildren.add(ependacc);
			emainChildren.add(etransaction);
			emain.setChildren(emainChildren);
		} else {
			System.out.println("Error in UpdateNodesAfterLogin!");
		}
		
	}
}
